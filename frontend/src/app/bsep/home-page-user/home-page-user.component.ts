import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Certificate } from "../certificate/Certificate";

import { User } from "../shared/model/User";
import { CertificateService } from "../shared/service/certificateService";
import { RegisterServices } from "../shared/service/registerService";

@Component({
    selector: 'pm-home-page-user',
    templateUrl: './home-page-user.component.html',
   
})
export class HomePageUserComponent implements OnInit {

    korisnik: User;
    request: Request;


    listaCASertifikata: Certificate[] = [];
    listaEESertifikata: Certificate[] = [];
    listaPovucenihSertifikata: Certificate[] = [];
    imenaAliasaCA: string[] = [];
    imenaAliasaEE: string[] = [];
    imenaAliasaSvi: string[] = [];
    povratnaSvi: boolean;


    constructor(private router: Router, private registracijaService: RegisterServices, private sertifikatService: CertificateService) {
        this.korisnik = new User();


    }

    ngOnInit(): void {
        this.vratiKorisnika();
    }


    vratiKorisnika() {

        this.registracijaService.whoIsLoggedIn().subscribe({
            next: korisnik => {
                this.korisnik = korisnik;

                if (korisnik == null) {
                    this.router.navigate(["/homepage"]);
                } else {
                    if (this.korisnik.rootCreated == true || this.korisnik.rootCreated == false) {
                        console.log("Instanca od ADMIN");
                        this.router.navigate(["/userpage"]);
                    } else {
                        console.log("Instanca od KORISNIK");
                        //this.router.navigate(["/homePage/korisnik"]);
                    }

                    this.sertifikatService.vratiSveCA().subscribe({
                        next: listaCASertifikata => {
                            this.listaCASertifikata = listaCASertifikata;
                            console.log(this.listaCASertifikata);
                        }
                    });

                    this.sertifikatService.vratiSveEE().subscribe({
                        next: listaEESertifikata => {
                            this.listaEESertifikata = listaEESertifikata;
                            console.log(this.listaEESertifikata);
                        }
                    });

                    this.sertifikatService.vratiSvePovucene().subscribe({
                        next: listaPovucenihSertifikata => {
                            this.listaPovucenihSertifikata = listaPovucenihSertifikata;
                        }
                    });

                    this.sertifikatService.vratiAliase().subscribe({
                        next: imenaAliasaCA => {
                            this.imenaAliasaCA = imenaAliasaCA;
                            console.log(this.imenaAliasaCA);
                        }
                    });

                    this.sertifikatService.vratiAliaseEE().subscribe({
                        next: imenaAliasaEE => {
                            this.imenaAliasaEE = imenaAliasaEE;
                            console.log(this.imenaAliasaEE);
                        }
                    });

                    this.sertifikatService.vratiAliaseSve().subscribe({
                        next: imenaAliasaSvi => {
                            this.imenaAliasaSvi = imenaAliasaSvi;
                            console.log(this.imenaAliasaSvi);
                        }
                    });
                }
            }
        });
    }

    validacijaSertifikatSvi(izabraniAliasSvi: string) {
        console.log(izabraniAliasSvi);
        this.sertifikatService.validacijaSvi(izabraniAliasSvi).subscribe({ next: povratnaSvi => { this.povratnaSvi = povratnaSvi; } });
    }

    skiniSertifikatCA(uid: string) {
        console.log(uid);
        this.sertifikatService.skiniCA(uid).subscribe();
    }

    skiniSertifikatEE(uid: string) {
        console.log(uid);
        this.sertifikatService.skiniEE(uid).subscribe();
    }

    logout() {
        this.registracijaService.logout(this.request).subscribe(
            result => this.kraj()
        );
    }

    kraj() {
        //this.router.navigate(["/welcome"]);
        alert("izlogovan")
    }
}