import { Component, OnInit } from '@angular/core';
import { ReclamationService } from '../services/reclamation.service';
import { Reclamation } from '../models/reclamation.model';
import { Router } from '@angular/router'; 
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-reclamation',
  templateUrl: './reclamation.component.html',
  styleUrls: ['./reclamation.component.css']
})
export class ReclamationComponent implements OnInit {
  reclamations: Reclamation[] = [];
  
  errorMessage: string | null = null;
  isLoading = false;
  currentPage = 1;
  itemsPerPage = 3;  
  pagedReclamations: Reclamation[] = [];


  constructor(
    private reclamationService: ReclamationService,
    private toastr: ToastrService,
    private router: Router) {}

  ngOnInit(): void {
    this.fetchReclamations();
  }

  fetchReclamations(): void {
    this.reclamationService.getAllReclamations().subscribe({
      next: (data) => {
        this.reclamations = data;
        console.log('Fetched reclamations:', this.reclamations);
      },
      error: (err) => console.error('Erreur lors du chargement des réclamations:', err)
    });
  }

  deleteReclamation(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette réclamation ?')) {
      this.reclamationService.delete(id).subscribe({
        next: () => {
          this.reclamations = this.reclamations.filter((rec) => rec.idReclamation !== id);
          console.log('Réclamation supprimée');
          this.toastr.success('Réclamation supprimée avec succès!', 'Succès');
        },
        error: (err) => {
          console.error('Erreur lors de la suppression de la réclamation:', err);
        }
      });
    }
  }
  editReclamation(id: number): void {
    this.router.navigate([`reclamation/update/${id}`]);
  }

  getEtatClass(etat: string): string {
    return etat;
  }
  onPageChange(page: number): void {
    this.currentPage = page;
    this.pagedReclamations = this.getPageReclamations(page);
  }
  // Get reclamations for the current page
  getPageReclamations(page: number): Reclamation[] {
    const startIndex = (page - 1) * this.itemsPerPage;
    return this.reclamations.slice(startIndex, startIndex + this.itemsPerPage);
  }

  goToReclamationDetails(id?: number): void {
    if (id !== undefined) {
      this.router.navigate(['/reclamation', id]);
    } else {
      console.warn('Reclamation ID is undefined');
    }
  }

  sortByState(): void {
    this.reclamations.sort((a, b) => a.etatReclamation.localeCompare(b.etatReclamation));
    this.pagedReclamations = this.getPageReclamations(this.currentPage);  // Re-apply pagination after sort
  }

  isUrgentReclamation(rec: Reclamation): boolean {
    const urgentKeywords = ['scam', 'fraud', 'lost package', 'refund'];
    const text = `${rec.titreReclamation} ${rec.descriptionReclamation}`.toLowerCase();
    return urgentKeywords.some(keyword => text.includes(keyword));
  }
  

  getUrgentClass(rec: Reclamation): any {
    const isUrgent = this.isUrgentReclamation(rec) || rec.etatReclamation === 'URGENT';
    return {
      'urgent-text': isUrgent,
      'normal-text': !isUrgent
    };
  }

  
}
