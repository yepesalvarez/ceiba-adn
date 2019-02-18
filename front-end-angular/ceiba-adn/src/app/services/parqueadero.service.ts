import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import 'rxjs/add/operator/map';
import { GLOBAL } from './global';
import { Vehiculo } from '../models/vehiculo';

@Injectable()
export class ParqueaderoService {
    public url: string;
    constructor(private http: HttpClient) {
        this.url = GLOBAL.url_parqueadero;
    }

    public obtenerVehiculosParqueadero() {
        return this.http.get<Vehiculo[]>(this.url);
    }

    ingresarVehiculo(vehiculoDto: Vehiculo) {
        return this.http.post<Vehiculo[]>(this.url, vehiculoDto);
    }

    retirarVehiculo(idVehiculo: number) {
        return this.http.delete<Vehiculo[]>(`${this.url}/${idVehiculo}`);
    }

    pagarParqueadero(idVehiculo: number) {
        let params = new HttpParams();
        params = params.append('idVehiculo', idVehiculo.toString());
        return this.http.patch<number>(this.url, params);
    }

}
