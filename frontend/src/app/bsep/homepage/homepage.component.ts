  
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../shared/model/User';
import { RegisterServices } from '../shared/service/registerService';

@Component({
  selector: 'homepage',
  templateUrl: './homepage.component.html'
})

export class HomePageComponent implements OnInit {
  korisnik: User;
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

        
      }
    });
  }
}
