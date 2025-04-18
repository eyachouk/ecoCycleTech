import { ReservationConfirmationDialogComponent } from './../comfirmationdialog/comfirmationdialog.component';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { ReservationService } from '../services/reservation.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AppareilService } from '../services/appareil.service';
import { Appareil } from '../model/appareil';
import { Reservation } from '../model/reservation';
import { PanierService } from '../services/panier.service';
import { MatDialog } from '@angular/material/dialog';
@Component({
  selector: 'app-reservation',
  templateUrl: './add-reservation.component.html',
  styleUrls: ['./add-reservation.component.css']
})
export class AddReservationComponent implements OnInit, OnDestroy {
  reservationForm: FormGroup;
  isLoading = false;
  errorMessage: string | null = null;
  allAppareils: Appareil[] = [];
  isEditMode = false;
  currentReservationId: number | null = null;
  private panierTimeout: any;
  appareilsInCart: Appareil[]=[];

  constructor(
    private reservationService: ReservationService,
    private appService: AppareilService,
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private panierService: PanierService
  ) {
    this.reservationForm = this.initForm();
  }

  ngOnInit(): void {
    this.loadAppareils();
    this.checkForEditMode();
    this.checkForPreSelectedAppareil();
    this.setupPanierTimeout();
    this.appareilsInCart = this.panierService.getAppareilsInCart();

  }

  private setupPanierTimeout(): void {
    this.panierTimeout = setTimeout(() => {
      this.panierService.clearPanier();
      if (!this.isEditMode && this.appareilsArray.length > 0) {
        this.router.navigate(['/appareils']);
      }
    }, 7200000);
  }

  ngOnDestroy(): void {
    if (this.panierTimeout) {
      clearTimeout(this.panierTimeout);
    }
  }

  private initForm(): FormGroup {
    return new FormGroup({
      client: new FormControl('', [Validators.required, Validators.minLength(3)]),
      date: new FormControl('', Validators.required),
      statut: new FormControl('Pending', Validators.required),
      appareils: new FormArray([], Validators.required),
      selectedAppareil: new FormControl(null),
      total: new FormControl(0, [Validators.required, Validators.min(0.01)])
    });
  }

  private checkForEditMode(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.currentReservationId = +params['id'];
        this.loadReservationForEdit(this.currentReservationId);
      }
    });
  }

  private loadReservationForEdit(reservationId: number): void {
    this.reservationService.getReservationById(reservationId).subscribe({
      next: (reservation: Reservation) => {
        this.reservationForm.patchValue({
          client: reservation.user,
          date: reservation.date,
          statut: reservation.statut,
          total: reservation.total
        });

        while (this.appareilsArray.length) {
          this.appareilsArray.removeAt(0);
        }

        reservation.panier.forEach(appareil => {
          this.appareilsArray.push(this.createAppareilFormGroup(appareil));
        });
      },
      error: (err) => {
        this.errorMessage = 'Failed to load reservation';
      }
    });
  }

  private checkForPreSelectedAppareil(): void {
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras?.state?.['appareil']) {
      this.addAppareilToForm(navigation.extras.state['appareil']);
      return;
    }

    if (history.state?.appareil) {
      this.addAppareilToForm(history.state.appareil);
      return;
    }

    this.route.paramMap.subscribe(params => {
      const appareilId = params.get('appareilId');
      if (appareilId) {
        this.loadAppareilById(+appareilId);
      }
    });
  }

  private loadAppareilById(id: number): void {
    this.appService.getAppareilById(id).subscribe({
      next: (appareil) => this.addAppareilToForm(appareil),
      error: (err) => {
        console.error('Error loading appareil:', err);
        this.errorMessage = 'Failed to load selected device';
      }
    });
  }

  private addAppareilToForm(appareil: Appareil): void {
    if (!this.isAppareilInForm(appareil.idAppareil)) {
      this.appareilsArray.push(this.createAppareilFormGroup(appareil));
      this.calculateTotal();
      this.reservationForm.get('selectedAppareil')?.setValue(appareil.idAppareil);
    }
  }

  private isAppareilInForm(appareilId: number): boolean {
    return this.appareilsArray.controls.some(
      control => control.value.idAppareil === appareilId
    );
  }

  private createAppareilFormGroup(appareil: Appareil): FormGroup {
    return new FormGroup({
      idAppareil: new FormControl(appareil.idAppareil),
      nom: new FormControl(appareil.nom),
      prix: new FormControl(appareil.prix)
    });
  }

  get appareilsArray(): FormArray {
    return this.reservationForm.get('appareils') as FormArray;
  }

  loadAppareils(): void {
    this.appService.getAppareils().subscribe({
      next: (appareils) => this.allAppareils = appareils,
      error: (err) => this.errorMessage = 'Error loading devices list'
    });
  }

  onAddAppareil(): void {
    const selectedId = this.reservationForm.get('selectedAppareil')?.value;
    if (!selectedId) {
      this.errorMessage = 'Please select a device';
      return;
    }

    const selectedAppareil = this.allAppareils.find(a => a.idAppareil === selectedId);
    if (!selectedAppareil) {
      this.errorMessage = 'Device not found';
      return;
    }

    if (this.isAppareilInForm(selectedId)) {
      this.errorMessage = 'Device already added';
      return;
    }

    const dialogRef = this.dialog.open(ReservationConfirmationDialogComponent, {
      width: '350px',
      data: {
        title: 'Add Another Device?',
        message: 'Do you want to add another device to your reservation?',
        cancelText: 'No, Proceed to Reservation',
        confirmText: 'Yes, Add Another'
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.appareilsArray.push(this.createAppareilFormGroup(selectedAppareil));
        this.calculateTotal();
        this.reservationForm.get('selectedAppareil')?.reset();
        this.errorMessage = null;
      } else {
        this.appareilsArray.push(this.createAppareilFormGroup(selectedAppareil));
        this.calculateTotal();
        this.errorMessage = null;
      }
    });
  }

  onRemoveAppareil(index: number): void {
    this.appareilsArray.removeAt(index);
    this.calculateTotal();
  }

  private calculateTotal(): void {
    const total = this.appareilsArray.controls.reduce(
      (sum, control) => sum + control.value.prix,
      0
    );
    this.reservationForm.get('total')?.setValue(total);
  }

  onSubmit(): void {
    if (this.reservationForm.invalid) {
      this.errorMessage = 'Please fill all required fields correctly';
      return;
    }

    this.isLoading = true;
    const reservationData = this.reservationForm.value;

    if (this.isEditMode && this.currentReservationId) {
      this.reservationService.updateReservation(reservationData, this.currentReservationId)
        .subscribe({
          next: () => this.router.navigate(['Reservations']),
          error: (err) => {
            this.isLoading = false;
            this.errorMessage = 'Failed to update reservation';
          }
        });
    } else {
      this.reservationService.createReservation(reservationData)
        .subscribe({
          next: () => {
            this.panierService.clearPanier();
            this.router.navigate(['Reservations']);
          },
          error: (err) => {
            this.isLoading = false;
            this.errorMessage = 'Reservation failed';
          }
        });
    }
  }
}
