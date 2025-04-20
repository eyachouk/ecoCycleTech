import { Component, HostListener } from '@angular/core';
import { ReclamationService } from '../../services/reclamation.service';
import { Reclamation } from '../../models/reclamation.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  constructor(
    public router: Router,
    private reclamationService: ReclamationService
  ) {}

  ngOnInit(): void {
    this.fetchAllReclamations();
  }

  reclamations: Reclamation[] = [];
  errorMessage: string | null = null;
  isLoading = false;

  goToReclamationDetails(): void {
    const reclamationId = 1; // You may dynamically fetch this ID
    this.router.navigate(['/reclamation', reclamationId]);
  }

  goToAddReclamation(): void {
    this.router.navigate(['add/reclamation']);
  }

  goToReclamationsList(): void {
    this.router.navigate(['/reclamations']);
  }

  goToAllReclamationDetails(): void {
    this.router.navigate(['/claim']);
  }







  fetchAllReclamations(): void {
    this.isLoading = true;
    this.reclamationService.getAllReclamations().subscribe(
      (data) => {
        this.reclamations = data;
        this.isLoading = false;
      },
      (error) => {
        this.errorMessage = 'Error fetching all reclamations.';
        this.isLoading = false;
        console.error('Error fetching reclamations:', error);
      }
    );
  }

  consultReclamation(id: number): void {
    this.isLoading = true;
    this.reclamationService.getById(id).subscribe(
      (data) => {
        this.reclamations = this.reclamations.map(r => r.idReclamation === id ? data : r);
        this.isLoading = false;
      },
      (error) => {
        this.errorMessage = 'Error fetching reclamation details.';
        this.isLoading = false;
        console.error('Error fetching reclamation:', error);
      }
    );
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    const target = event.target as HTMLElement;
    const clickedInsideNav = target.closest('.nav-element');
    const clickedInsideDropdown = target.closest('.dropdown-menu');

    if (!clickedInsideNav && !clickedInsideDropdown) {
      document.querySelectorAll('.dropdown-menu').forEach((el) => {
        el.classList.add('hidden');
      });

      document.querySelectorAll('.nav-element.active').forEach((el) => {
        el.classList.remove('active');
      });
    }
  }

  SetActive(event: MouseEvent) {
    const element = event.target as HTMLElement;
    const parentElement = element.closest('.nav-element') as HTMLElement;
    const dropdownelement = parentElement.querySelector('.dropdown-menu') as HTMLElement;

    console.log(parentElement);
    console.log(dropdownelement);

    if (parentElement.classList.contains('active')) {
      parentElement.classList.remove('active');
      dropdownelement.classList.add('hidden');
    } else {
      document.querySelectorAll('.active').forEach((el) => {
        el.classList.remove('active');
      });

      document.querySelectorAll('.dropdown-menu').forEach((dl) => {
        dl.classList.add('hidden');
      });

      if (parentElement) {
        parentElement.classList.add('active');
        dropdownelement.classList.remove('hidden');
        dropdownelement.classList.add('scalein');
      }
    }
  }
}