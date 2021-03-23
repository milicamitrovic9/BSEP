import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import {
  AgmCoreModule
} from '@agm/core';
import { RegisterComponent } from './bsep/register/register.component';
import { LoginComponent } from './bsep/login/login.component';
import { HomePageComponent } from './bsep/homepage/homepage.component';
import { RegisterServices } from './bsep/shared/service/registerService';
import { UserPageComponent } from './bsep/userpage/userpage.component';
import { User } from './bsep/shared/model/User';



@NgModule({
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    //AppRoutingModule,
  
    RouterModule.forRoot([
      { path: 'homepage', component: HomePageComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'login', component: LoginComponent },
      { path: 'userpage', component: UserPageComponent },
      //{ path: 'homePage', component: HomePageComponent },
      //{ path: 'sertifikat', component:SertifikatComponent },
      //{ path: 'listaSertifikata', component:ListaSertifikataComponent },
      { path: '', redirectTo: 'homepage', pathMatch: 'full' },
      { path: '**', redirectTo: 'homepage', pathMatch: 'full'}
    ]),
    FormsModule
   
  ],
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    HomePageComponent,
    UserPageComponent
       
  ],

  providers: [RegisterServices],

  bootstrap: [AppComponent]
})
export class AppModule { }
