import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { User } from "../model/User";
import { Login } from "../model/Login";
import { Observable } from "rxjs";



@Injectable()
export class RegisterServices{
    private pacijetUrl:string;

    constructor(private http:HttpClient){}
    
  
 

    public save(user:User){
        return this.http.post<User>("/api/admin/add",user);
    }

    public login(loginReq:Login){
        return this.http.post<Response>("/api/auth/login", loginReq);
    }
 
    public whoIsLoggedIn():Observable<User>{
        return this.http.get<User>("/api/auth/loggedUser");
        
    }

    public logout(request: Request) {
        return this.http.put("/api/auth/logOut", request);
    }
    public saveObicnog(korisnik:User){
        return this.http.post<User>("/api/auth/regKorisnika",korisnik);
    }

}