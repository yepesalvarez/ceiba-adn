import { CeibaAdnFrontPage } from './app.po';

describe('ceiba-adn-front App', function() {
  let page: CeibaAdnFrontPage;

  beforeEach(() => {
    page = new CeibaAdnFrontPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
