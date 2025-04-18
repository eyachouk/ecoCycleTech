import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Appareil } from '../model/appareil';
import { ActivatedRoute, Router } from '@angular/router';
import { AppareilService } from '../services/appareil.service';
import { EtatAppareilEnum } from '../model/etat-appareil.enum';  // Importer l'énumération

@Component({
  selector: 'app-add-appareil',
  templateUrl: './add-appareil.component.html',
  styleUrls: ['./add-appareil.component.css']
})
export class AddAppareilComponent {
  AppareilForm!: FormGroup;
  idAppareil!: number;
  appareil!: Appareil;
  etatAppareilOptions: string[] = Object.values(EtatAppareilEnum);

  // getEtatAppareilOptions() {
  //   return Object.keys(this.EtatAppareil).filter(key => isNaN(Number(key))).map(key => this.EtatAppareil[key as keyof typeof EtatAppareilEnum]);
  // }

  constructor(private as: AppareilService, private route: Router, private act: ActivatedRoute) {
    this.AppareilForm = new FormGroup({
      nom: new FormControl('', [Validators.required, Validators.minLength(4)]),
      categorie: new FormControl('', Validators.required),
      etatAppareil: new FormControl('', Validators.required),
      marque: new FormControl('', Validators.required),
      imageurl: new FormControl('https://example.com/images/dell-xps-13.jpg', [Validators.required]),
      prix: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });
  }

  ngOnInit() {
    this.idAppareil = this.act.snapshot.params['id'];

    if (this.idAppareil) {
      this.as.getAppareilById(this.idAppareil).subscribe(
        (data) => {
          this.appareil = data;
          etatAppareil: this.appareil.etatAppareil as EtatAppareilEnum
          console.log(this.appareil);
          this.AppareilForm.patchValue(this.appareil);
        }
      );
    }
  }


  save() {
    if (this.idAppareil) {
      this.as.updateAppareil(this.AppareilForm.value, this.idAppareil).subscribe(() => this.route.navigateByUrl('admin/Appareils'));
    } else {
      this.as.createAppareil(this.AppareilForm.value).subscribe(() => this.route.navigateByUrl('admin/Appareils'));
    }
  }
}
