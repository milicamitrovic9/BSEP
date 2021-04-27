import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { RegisterServices } from '../shared/service/registerService';
import { UserPageComponent } from './userpage.component';
import { CertificateComponent } from '../certificate/certificate.component';
import { ListaSertifikataComponent } from '../allcertificates/allCertificates.component';
import { CertificateService } from '../shared/service/certificateService';
import { HomePageUserComponent } from '../home-page-user/home-page-user.component';




@NgModule({
  declarations: [
    UserPageComponent,
    CertificateComponent,
    ListaSertifikataComponent,
    HomePageUserComponent
   
  ],
  imports: [
    CommonModule,
    RouterModule.forChild([
      { path: 'userpage', component: UserPageComponent },
      { path: 'userpage/certificate', component:CertificateComponent },
      { path: 'userpage/allCertificates', component:ListaSertifikataComponent },
      { path: 'userpage/korisnik', component: HomePageUserComponent }

    ]),
    FormsModule, 
  ],
  providers:  [
      RegisterServices,CertificateService

  ]
})
export class UserPageModule { }