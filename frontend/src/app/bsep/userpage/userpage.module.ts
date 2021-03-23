import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { RegisterServices } from '../shared/service/registerService';
import { UserPageComponent } from './userpage.component';




@NgModule({
  declarations: [
    UserPageComponent,
   
  ],
  imports: [
    CommonModule,
    RouterModule.forChild([
      { path: 'userpage', component: UserPageComponent },
      
      

    ]),
    FormsModule, 
  ],
  providers:  [
      RegisterServices,
  ]
})
export class UserPageModule { }