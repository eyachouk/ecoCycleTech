import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FactureService {
  private apiUrl = 'http://localhost:8090/ecoCycleTech/api/factures';

  constructor(private http: HttpClient) {}
  downloadFacture(factureId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${factureId}/download`, {
      responseType: 'blob',
    });
  }

}
