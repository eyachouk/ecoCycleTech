import { Component, OnInit } from '@angular/core';
import { Avis } from '../model/avis';
import { Appareil } from '../model/appareil';
import { User } from '../model/user';
import { AvisService } from '../services/avis.service';
import { AppareilService } from '../services/appareil.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { BadWordsService } from '../services/bad-words.service';

@Component({
  selector: 'app-add-avis',
  templateUrl: './add-avis.component.html',
  styleUrls: ['./add-avis.component.css']
})
export class AddAvisComponent implements OnInit {
  avisList: Avis[] = [];
  AvisForm!: FormGroup;
  appareils: Appareil[] = [];
  users: User[] = [];
  idAvis!: number;
  isEditMode: boolean = false;

  constructor(
    private avisService: AvisService,
    private appareilService: AppareilService,
    private route: Router,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private badWordService: BadWordsService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadAppareils();

    const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    if (idParam) {
      this.idAvis = +idParam;
      this.isEditMode = true;
      this.loadAvisById(this.idAvis);
    }
  }

  initForm(): void {
    this.AvisForm = this.fb.group({
      contenu: ['', Validators.required],
      appareil: [null, Validators.required],
      rating: new FormControl(0, [Validators.required, Validators.min(1), Validators.max(5)]),
      user: null
    });
  }

  loadAppareils(): void {
    this.appareilService.getAppareils().subscribe(data => this.appareils = data);
  }

  setRating(rating: number): void {
    this.AvisForm.get('rating')?.setValue(rating);
  }

  loadAvisById(idAvis: number): void {
    this.avisService.getAvisById(idAvis).subscribe(data => {
      this.AvisForm.patchValue({
        contenu: data.contenu,
        appareil: data.appareil,
        user: data.user
      });
    });
  }

  saveAvis(): void {
    const filteredContent = this.badWordService.filter(this.AvisForm.get('contenu')?.value);
    const avisToSave = { ...this.AvisForm.value, contenu: filteredContent };

    if (this.isEditMode) {
      this.avisService.updateAvis(this.idAvis, avisToSave).subscribe(() => {
        this.route.navigateByUrl('Avis');
      });
    } else {
      this.avisService.addAvis(avisToSave).subscribe(() => {
        this.route.navigateByUrl('Avis');
      });
    }
  }

  resetForm(): void {
    this.AvisForm.reset();
    this.isEditMode = false;
  }
}
