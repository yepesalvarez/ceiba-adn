import { browser, by, element, promise } from 'protractor';

export class AppPage {

  navigateTo() {
    return browser.get(browser.baseUrl) as Promise<any>;
  }

  setInputTextPlaca(placa: string) {
    return element(by.id('placa')).sendKeys(placa);
  }

  clickBotonCarroTipoVehiculo() {
    element(by.id('carro-button')).click();
  }

  clickBotonIngresar() {
    element(by.id('ingresar-button')).click();
  }

  getCantidadVehiculosIngresados() {
    return element.all(by.name('nuevo-vehiculo')).count();
  }

  existeVehiculoEnParqueadero(placa: string) {
    return element.all(by.id(placa)).isPresent();
  }

  clickBotonCobrarParqueadero(placa: string) {
    element(by.id(placa)).element(by.className('btn btn-info')).click();
  }

  clickBotonPagarParqueadero(placa: string) {
    element(by.id(placa)).element(by.className('btn btn-success')).click();
  }

  clickBotonRetirarParqueadero(placa: string) {
    element(by.id(placa)).element(by.className('btn btn-danger')).click();
  }

  generarPlacaAleatoria(): string {
    let placa = '';
    const numerosPlaca = Math.floor(Math.random() * 999) + 100;
    const posibleLetra = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    for (let i = 0; i < 3 ; i++) {
      placa += posibleLetra.charAt(Math.floor(Math.random() * posibleLetra.length));
    }
    placa = placa.concat(numerosPlaca.toString());
    return placa;
  }

}
