  
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Login } from '../shared/model/Login';
import { User } from '../shared/model/User';
import { RegisterServices } from '../shared/service/registerService';

@Component({
  //selector: 'login',
  templateUrl: './login.component.html'
})


export class LoginComponent implements OnInit{
  user:User;
  respnse:Response;
  request:Login;
  
  constructor(private router:Router,private loginService:RegisterServices){

      this.request=new Login();
      this.user=new User();
    
  }

  ngOnInit() {

}


  login(){
      
      this.loginService.login(this.request).subscribe(result=>this.vratiKorisnika());
     
    
  }

  vratiKorisnika(){

     this.loginService.whoIsLoggedIn().subscribe({next: korisnik=>{
          this.user=korisnik;
          alert("User "+korisnik.name + " logged in successfully.");
          if (this.user.rootCreated == true || this.user.rootCreated == false) {
            console.log("Instanca od ADMIN");
            this.router.navigate(["/userpage"]);
          } else {
            console.log("Instanca od KORISNIK");
            this.router.navigate(["/userpage/korisnik"]);
        }
          
     }
     });
     
          
               
            
      

      
  
     }
     
    

}