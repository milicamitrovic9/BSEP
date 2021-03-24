import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Certificate } from "app/bsep/certificate/Certificate";




@Injectable()
export class CertificateService{
    private pacijetUrl:string;

    constructor(private http:HttpClient){
      
    }
  
 

    public sacuvaj(sertifikat:Certificate){
        return this.http.post<boolean>("/api/certificate/create",sertifikat);
    }

    public sacuvajRoot(sertifikat:Certificate){
        return this.http.post<Certificate>("/api/certificate/createRoot",sertifikat);
    }
 
    public vratiAliase():Observable<string[]>{
        return this.http.get<string[]>("/api/certificate/aliasi");
    
    }

    public vratiAliaseEE():Observable<string[]>{
        return this.http.get<string[]>("/api/certificate/aliasiEE");
    
    }

    public vratiAliaseSve():Observable<string[]>{
        return this.http.get<string[]>("/api/certificate/aliasiSvi");
    
    }

    public vratiSveCA():Observable<Certificate[]>{
        return this.http.get<Certificate[]>("/api/certificate/sviCASertifikati");
    
    }
    public vratiSveEE():Observable<Certificate[]>{
        return this.http.get<Certificate[]>("/api/certificate/sviEESertifikati");
    
    }
    public skiniCA(uid:String){
        return this.http.get("/api/certificate/downloadCA/"+uid);
    
    }
    public skiniEE(uid:String){
        return this.http.get("/api/certificate/downloadEE/"+uid);
    
    }

    public povuciCA(uid: String){
        return this.http.get("/api/certificate/povuciCA/" + uid);    
    }

    public povuciEE(uid: String){
        return this.http.get("/api/certificate/povuciEE/" + uid);    
    }

    public vratiSvePovucene(): Observable<Certificate[]> {
        return this.http.get<Certificate[]>("/api/certificate/sviPovuceniSertifikati");
    
    }

    public validacijaCA(izabraniAliasCA:String){
        return this.http.get<boolean>("/api/certificate/validacijaCA/"+izabraniAliasCA);
    
    }

    public validacijaEE(izabraniAliasEE:String){
        return this.http.get<boolean>("/api/certificate/validacijaEE/"+izabraniAliasEE);
    
    }

    public validacijaSvi(izabraniAliasSvi:String){
        return this.http.get<boolean>("/api/certificate/validacijaSvi/"+izabraniAliasSvi);
    
    }

}