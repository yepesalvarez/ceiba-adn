import { Injectable } from '@angular/core';
import { Http, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/map';
import { GLOBAL } from './global';

@Injectable()
export class CobroService {
    public url: string;
    constructor(private _http: Http){
        this.url = GLOBAL.url_cobro;
    }

    public obtenerValorCobro(idVehiculo: number, fechaHoraSalida: string) {
        const params = new URLSearchParams();
        params.append('idVehiculo', idVehiculo.toString());
        params.append('finParqueo', fechaHoraSalida);
        return this._http.post(this.url, params).map(res => res.json());
    }
}
