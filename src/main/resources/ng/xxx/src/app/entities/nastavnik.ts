import { EKorisnikUloga } from '../enum/ekorisnik-uloga.enum';
import { Predaje } from './predaje';

export class Nastavnik {
    id: Number;
    ime: string;
    prezime: string;
    korisnickoIme: string;
    email: string;
    lozinka: string;
    uloga: EKorisnikUloga;
    zvanje: string;
    predaje: Predaje [];

}
