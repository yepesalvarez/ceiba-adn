import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import 'rxjs/add/operator/map';
import { GLOBAL } from '../services/global';

@Injectable()
export class CobroService {
    public url: string;
    constructor(private http: HttpClient) {
        this.url = GLOBAL.url_cobro;
    }

    public obtenerValorCobro(idVehiculo: number, fechaHoraSalida: string) {
        let params = new HttpParams();
        params = params.append('idVehiculo', idVehiculo.toString());
        params = params.append('finParqueo', fechaHoraSalida);
        return this.http.post<string>(this.url, params);
    }
}
