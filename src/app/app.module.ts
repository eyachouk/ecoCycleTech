import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LayoutModule } from './client/layout/layout.module';
import { AccueilComponent } from './client/accueil/accueil.component';
import { AddPlanStockageComponent } from './admin/GestionPlansStockage/add-plan-stockage/add-plan-stockage.component';
import { PlanStockageListComponent } from './admin/GestionPlansStockage/plan-stockage-list/plan-stockage-list.component';
import { AdminLayoutModule } from './admin/admin-layout/admin-layout.module';
import { ReclamationComponent } from './reclamation/reclamation.component';
import { HttpClientModule } from '@angular/common/http';
import { ReclamationListComponent } from './reclamation-list/reclamation-list.component';
import { ReclamationDetailsComponent } from './client/reclamation-details/reclamation-details.component';
import { AddReclamationComponent } from './reclamation/add-reclamation/add-reclamation.component';
import { FormsModule } from '@angular/forms';
import { UpdateReclamationComponent } from './reclamation/update-reclamation/update-reclamation.component'; 
import { DropdownModule } from 'primeng/dropdown';
import { RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { ListReclamationAdminComponent } from './admin/list-reclamation-admin/list-reclamation-admin.component';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { UpdateReclamationAdminComponent } from './admin/update-reclamation-admin/update-reclamation-admin.component';
import { ListsupportReclamationComponent } from './admin/support-reclamation/listsupport-reclamation/listsupport-reclamation.component';
import { AddSupportReclamationComponent } from './admin/add-support-reclamation/add-support-reclamation.component';
import { ReclamationChartComponent } from './admin/reclamation-chart/reclamation-chart.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideToastr } from 'ngx-toastr'; //YEFA
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { EditDialogComponent } from './support-reclamation/edit-dialog/edit-dialog.component'; //YEFA
import { NgxCaptchaModule } from 'ngx-captcha';
import { RecaptchaModule } from 'ng-recaptcha';
import { RecaptchaV3Module, RECAPTCHA_V3_SITE_KEY } from 'ng-recaptcha';
@NgModule({
  declarations: [
    AppComponent,
    AccueilComponent,
    AddPlanStockageComponent,
    PlanStockageListComponent,
    ReclamationComponent,
    ReclamationListComponent,
    ReclamationDetailsComponent,
    AddReclamationComponent,
    UpdateReclamationComponent,
    ListReclamationAdminComponent,
    UpdateReclamationAdminComponent,
    ListsupportReclamationComponent,
    AddSupportReclamationComponent,
    ReclamationChartComponent,
    EditDialogComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LayoutModule,
    BrowserAnimationsModule,
    AdminLayoutModule,
    HttpClientModule,
    FormsModule,
    DropdownModule,
    RouterModule,
    AppRoutingModule,
    ButtonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    NgxPaginationModule, //YEFA
    ProgressSpinnerModule, //YEFA
    ToastrModule.forRoot(), //YEFA
    NgxCaptchaModule,//YEFA
    RecaptchaModule,
    RecaptchaV3Module,//YEFA
  ],
  providers: [
    provideAnimations(), // required animations providers
    provideToastr(), // Toastr providers
    { provide: RECAPTCHA_V3_SITE_KEY, useValue: '6LdsBx4rAAAAAOeT1gdoXy_XpUi0b2WiuzIvUnX0' }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
