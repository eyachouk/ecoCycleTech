// src/app/appareil/appareil.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Appareil } from '../model/appareil';

@Injectable({
  providedIn: 'root',
})
export class AppareilService {
  getRecommendations(query: string) {
    throw new Error('Method not implemented.');
  }
  private apiUrl = 'http://localhost:8090/ecoCycleTech/api/appareils';

  constructor(private http: HttpClient) {}

  createAppareil(appareil: Appareil): Observable<Appareil> {
    return this.http.post<Appareil>(this.apiUrl, appareil);
  }

  getAppareils(): Observable<Appareil[]> {
    return this.http.get<Appareil[]>(this.apiUrl);
  }

  getAppareilById(id: number): Observable<Appareil> {
    return this.http.get<Appareil>(`${this.apiUrl}/${id}`);
  }

  updateAppareil(appareil: Appareil,id: number): Observable<Appareil> {
    return this.http.put<Appareil>(`${this.apiUrl}/${id}`, appareil);
  }

  deleteAppareil(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
getAverageRating(appareilId: number): Observable<number> {
  return this.http.get<number>(`http://localhost:8090/ecoCycleTech/api/avis/average/${appareilId}`);
}
}
