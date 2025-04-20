import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from './client/layout/layout.component';
import { AccueilComponent } from './client/accueil/accueil.component';
import { AdminLayoutComponent } from './admin/admin-layout/admin-layout.component';
import { ReclamationComponent } from './reclamation/reclamation.component';
import { ReclamationDetailsComponent } from './client/reclamation-details/reclamation-details.component'; // Adjust the path as needed
import { ReclamationListComponent } from './reclamation-list/reclamation-list.component';
import { NavbarComponent } from './client/navbar/navbar.component';  // A
import { AddReclamationComponent } from './reclamation/add-reclamation/add-reclamation.component';
import { UpdateReclamationComponent } from './reclamation/update-reclamation/update-reclamation.component';
import { ListReclamationAdminComponent } from './admin/list-reclamation-admin/list-reclamation-admin.component';
import { UpdateReclamationAdminComponent } from './admin/update-reclamation-admin/update-reclamation-admin.component';
import { ListsupportReclamationComponent } from './admin/support-reclamation/listsupport-reclamation/listsupport-reclamation.component';
import { AddSupportReclamationComponent } from './admin/add-support-reclamation/add-support-reclamation.component';
import { ReclamationChartComponent } from './admin/reclamation-chart/reclamation-chart.component';

const routes: Routes = [
  {
    path: '', component: LayoutComponent,
    children: [
      { path: '', component: AccueilComponent },
      { path: 'add/reclamation', component: AddReclamationComponent }, 
      { path: 'reclamation/update/:id',  component:UpdateReclamationComponent  },  
      { path: 'claim', component: ReclamationComponent },
    ]
    
},
{ path: 'claim', component: ReclamationComponent },
{
  
    path: 'admin',
    component: AdminLayoutComponent,
    children: [
      { path: 'list/reclamation', component: ListReclamationAdminComponent },
      { path: 'update/reclamation/:id', component: UpdateReclamationAdminComponent },
      { path: 'listSupportReclamation', component: ListsupportReclamationComponent },
      { path: 'supportreclamtion/create', component: AddSupportReclamationComponent },
      { path: 'chart', component: ReclamationChartComponent }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
