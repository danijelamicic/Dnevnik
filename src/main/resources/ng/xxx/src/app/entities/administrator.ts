import { EKorisnikUloga } from '../enum/ekorisnik-uloga.enum';

export class Administrator {
    id: Number;
    ime: string;
    prezime: string;
    korisnickoIme: string;
    email: string;
    lozinka: string;
    uloga: EKorisnikUloga;

}
