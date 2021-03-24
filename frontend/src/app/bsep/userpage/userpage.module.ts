import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { RegisterServices } from '../shared/service/registerService';
import { UserPageComponent } from './userpage.component';
import { CertificateComponent } from '../certificate/certificate.component';
import { ListaSertifikataComponent } from '../allcertificates/allCertificates.component';
import { CertificateService } from '../shared/service/certificateService';




@NgModule({
  declarations: [
    UserPageComponent,
    CertificateComponent,
    ListaSertifikataComponent
   
  ],
  imports: [
    CommonModule,
    RouterModule.forChild([
      { path: 'userpage', component: UserPageComponent },
      { path: 'userpage/certificate', component:CertificateComponent },
      { path: 'userpage/allCertificates', component:ListaSertifikataComponent },

    ]),
    FormsModule, 
  ],
  providers:  [
      RegisterServices,CertificateService

  ]
})
export class UserPageModule { }