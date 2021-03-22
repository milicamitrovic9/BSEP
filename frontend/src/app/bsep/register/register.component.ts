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
    
    constructor(private route:ActivatedRoute,private router:Router,private registrcijaService:RegisterServices){
        this.korisnik=new User();
    }

    onSubmit() {
      
    
        this.registrcijaService.save(this.korisnik).subscribe(result => this.gotoUserList());
        alert("sacuvan korisnik "+this.korisnik.name+""+this.korisnik.lastname)
        this.korisnik.name="";
        this.korisnik.lastname="";
        this.korisnik.email="";
        this.korisnik.password="";
        this.router.navigate(["/login"]);

      }
     
      gotoUserList() {
       
      }

}