import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-paymentsuccess',
  templateUrl: './paymentsuccess.component.html',
  styleUrls: ['./paymentsuccess.component.css']
})
export class PaymentSuccessComponent implements OnInit {

  qrCodeUrl: string = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const reservationId = 1;
    this.generateFacture(reservationId);
  }

  generateFacture(reservationId: number) {
    this.http.post<any>(`http://localhost:8090/ecoCycleTech/api/factures/create/${reservationId}`, {})
      .subscribe({
        next: (facture) => {
          this.qrCodeUrl = `http://localhost:8090/ecoCycleTech/api/factures/${facture.id}/download`;
        },
        error: (error) => {
          console.error("Erreur lors de la création de la facture :", error);
        }
      });
  }
}
