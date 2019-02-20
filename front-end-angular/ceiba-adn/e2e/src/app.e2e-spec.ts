import { AppPage } from './app.po';
import { browser, logging } from 'protractor';

describe('workspace-project App', () => {
  let page: AppPage;

  let placaValida: string;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should display a new row for the new vehicle', () => {
    placaValida = page.generarPlacaAleatoria();
    page.navigateTo();
    page.setInputTextPlaca(placaValida);
    page.clickBotonCarroTipoVehiculo();
    page.clickBotonIngresar();
    browser.sleep(5000);
    const numeroVehiculos = page.getCantidadVehiculosIngresados();
    expect(numeroVehiculos).toBeGreaterThanOrEqual(1);
  });

  it('should delete the created vehicle', () => {
    page.navigateTo();
    page.clickBotonPagarParqueadero(placaValida);
    browser.sleep(3000);
    page.clickBotonRetirarParqueadero(placaValida);
    browser.sleep(4000);
    expect(page.existeVehiculoEnParqueadero(placaValida)).toBe(false);
  });

  afterEach(async () => {
    // Assert that there are no errors emitted from the browser
    // const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    // expect(logs).not.toContain(jasmine.objectContaining({
    //   level: logging.Level.SEVERE,
    // }));
  });
});




