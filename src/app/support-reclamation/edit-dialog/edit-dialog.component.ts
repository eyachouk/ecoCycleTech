import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SupportReclamation } from '../../models/support-reclamation.model';
@Component({
  selector: 'app-edit-dialog',
  templateUrl: './edit-dialog.component.html',
  styleUrls: ['./edit-dialog.component.css']
})

export class EditDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<EditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SupportReclamation
  ) {}

  onSave(): void {
    this.dialogRef.close(this.data); 
  }

  onCancel(): void {
    this.dialogRef.close(); 
  }
}
