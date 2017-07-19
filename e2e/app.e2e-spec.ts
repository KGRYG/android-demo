import { StoreFrontCustomPage } from './app.po';

describe('store-front-custom App', () => {
  let page: StoreFrontCustomPage;

  beforeEach(() => {
    page = new StoreFrontCustomPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
