import { Injectable } from '@angular/core';
import { Appareil } from '../model/appareil';

@Injectable({
  providedIn: 'root'
})
export class PanierService {
  private appareilsInCart: Appareil[] = [];

  constructor() {}

  // Add appareil to the cart
  ajouterAppareil(appareil: Appareil): void {
    this.appareilsInCart.push(appareil);
  }

  // Get the appareils in the cart
  getAppareilsInCart(): Appareil[] {
    return this.appareilsInCart;
  }

  // Clear the cart
  clearPanier(): void {
    this.appareilsInCart = [];
  }
}
