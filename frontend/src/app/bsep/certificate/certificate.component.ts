import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Certificate } from "./Certificate";
import { User } from "../shared/model/User";
import { CertificateService } from "../shared/service/certificateService";
import { RegisterServices } from "../shared/service/registerService";



@Component({

    //selector: 'login-component',
    templateUrl: './certificate.html',
    //styleUrls: []

})


export class CertificateComponent implements OnInit {

    sertifikat: Certificate;
    povratna: boolean = true;
    korisnik: User;
    imenaAliasa: string[] = [];
    izabraniAlias: string;

    constructor(private router: Router, private loginService: RegisterServices, private sertifikatService: CertificateService) {

        this.korisnik = new User();
        this.sertifikat = new Certificate();

    }

    ngOnInit() {
        this.loginService.whoIsLoggedIn().subscribe({
            next: korisnik => {
                this.korisnik = korisnik;
                if (this.korisnik == null) {
                    this.router.navigate(["/homepage"]);
                }
              
            }
        });
        this.sertifikatService.vratiAliase().subscribe({
            next: imenaAliasa => {
            this.imenaAliasa = imenaAliasa;
                console.log(this.imenaAliasa);
            }
        });


    }
    napraviNoviSertifikat() {

        console.log(this.sertifikat);
        this.sertifikat.issuer = this.izabraniAlias;
        console.log(this.izabraniAlias);

        if (this.sertifikat.notAfter < this.sertifikat.notBefore) {
            alert("Datum isticanja mora biti nakon datuma izdavanja.");
        }
        else
            this.sertifikatService.sacuvaj(this.sertifikat).subscribe({
                next: povratna => {
                this.povratna = povratna;
                    if (this.povratna == true)
                    this.router.navigate(['/userpage']);
                }
            });

    }

    gotoUserList() {

    }

    back(): void {
        this.router.navigate(['/userpage']);
    }


}
