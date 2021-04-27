import { Component } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { User } from "../shared/model/User";
import { RegisterServices } from "../shared/service/registerService";

@Component({
    selector: 'register',
    templateUrl : './register.html'

})

export class RegisterComponent{
    
    korisnik: User;
    
    korisnikUserReg: boolean = false;

    constructor(private route: ActivatedRoute, private router: Router, private registrcijaService: RegisterServices) {
        this.korisnik = new User();
    }

    onSubmit() {
      
    
        if (this.korisnikUserReg == false) {

            this.registrcijaService.save(this.korisnik).subscribe(result => this.gotoUserList());
            alert("sacuvan korisnik " + this.korisnik.name + "" + this.korisnik.lastName)
            this.korisnik.name = "";
            this.korisnik.lastName = "";
            this.korisnik.email = "";
            this.korisnik.password = "";
            this.router.navigate(["/login"]);
        } else {
            this.registrcijaService.saveObicnog(this.korisnik).subscribe(result => this.gotoUserList());
            alert("Sacuvan obican korisnik " + this.korisnik.name + "" + this.korisnik.lastName)
            this.korisnik.name = "";
            this.korisnik.lastName = "";
            this.korisnik.email = "";
            this.korisnik.password = "";
            this.router.navigate(["/login"]);
        }
    }

    toggleUserReg(event) {
        if (event.target.checked) {
            this.korisnikUserReg = true;
        } else {
            this.korisnikUserReg = false;
        }
    }


    gotoUserList() {

    }

}