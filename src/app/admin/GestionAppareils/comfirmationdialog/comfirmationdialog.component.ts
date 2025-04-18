import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Appareil } from '../model/appareil';

@Component({
  selector: 'app-reservation-confirmation-dialog',
  templateUrl: './comfirmationdialog.component.html',
  styles: [`
    .dialog-content {
      display: flex;
      flex-direction: column;
      gap: 15px;
    }
    .appareil-info {
      display: flex;
      align-items: center;
      gap: 15px;
      padding: 10px;
      border: 1px solid #eee;
      border-radius: 4px;
    }
    .appareil-image {
      width: 60px;
      height: 60px;
      object-fit: cover;
      border-radius: 4px;
    }
  `]
})
export class ReservationConfirmationDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<ReservationConfirmationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      title: string;
      message: string;
      appareil: Appareil;
      action: 'reserve' | 'add';
    }
  ) {}

  // Méthode pour annuler
  onCancelClick(): void {
    this.dialogRef.close(false); // Important pour fermer le dialog et retourner false
  }

  // Méthode pour confirmer
  onConfirmClick(): void {
    this.dialogRef.close(true); // Ferme le dialog et retourne true
  }
}
