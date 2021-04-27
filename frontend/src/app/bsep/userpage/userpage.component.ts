import { Component, OnInit } from '@angular/core';
import { Routes, Router } from '@angular/router';
import { User } from '../shared/model/User';
import { RegisterServices } from '../shared/service/registerService';

@Component({
  
    templateUrl: './userpage.html',
   
  
  })
  export class UserPageComponent {
  
    korisnik: User;
    request: Request;
  
    constructor(private router: Router, private registracijaService: RegisterServices) {
      this.korisnik = new User();
  
  
    }
  
    ngOnInit(): void {
      this.vratiKorisnika();
    }
  
  
    vratiKorisnika() {
  
      this.registracijaService.whoIsLoggedIn().subscribe({
        next: korisnik => {
          this.korisnik = korisnik;
          if (this.korisnik.rootCreated == true || this.korisnik.rootCreated == false) {
            console.log("Instanca od ADMIN");
            this.router.navigate(["/userpage"]);
          } else {
            console.log("Instanca od KORISNIK");
            this.router.navigate(["/userpage/korisnik"]);
        }
  
         
        }
      });
    }
  
    logout() {
      this.registracijaService.logout(this.request).subscribe(
        result => this.kraj()
      );
    }
  
    kraj() {
   //   this.router.navigate(["/homepage"]);
      alert("You have just logged out")
    }
  
  }