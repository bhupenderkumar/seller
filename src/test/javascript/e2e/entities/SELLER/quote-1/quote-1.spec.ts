import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { Quote1ComponentsPage, Quote1DeleteDialog, Quote1UpdatePage } from './quote-1.page-object';

const expect = chai.expect;

describe('Quote1 e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let quote1ComponentsPage: Quote1ComponentsPage;
  let quote1UpdatePage: Quote1UpdatePage;
  let quote1DeleteDialog: Quote1DeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Quote1s', async () => {
    await navBarPage.goToEntity('quote-1');
    quote1ComponentsPage = new Quote1ComponentsPage();
    await browser.wait(ec.visibilityOf(quote1ComponentsPage.title), 5000);
    expect(await quote1ComponentsPage.getTitle()).to.eq('haryaliApp.sellerQuote1.home.title');
    await browser.wait(ec.or(ec.visibilityOf(quote1ComponentsPage.entities), ec.visibilityOf(quote1ComponentsPage.noResult)), 1000);
  });

  it('should load create Quote1 page', async () => {
    await quote1ComponentsPage.clickOnCreateButton();
    quote1UpdatePage = new Quote1UpdatePage();
    expect(await quote1UpdatePage.getPageTitle()).to.eq('haryaliApp.sellerQuote1.home.createOrEditLabel');
    await quote1UpdatePage.cancel();
  });

  it('should create and save Quote1s', async () => {
    const nbButtonsBeforeCreate = await quote1ComponentsPage.countDeleteButtons();

    await quote1ComponentsPage.clickOnCreateButton();

    await promise.all([
      quote1UpdatePage.setSymbolInput('symbol'),
      quote1UpdatePage.setPriceInput('5'),
      quote1UpdatePage.setLastTradeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await quote1UpdatePage.getSymbolInput()).to.eq('symbol', 'Expected Symbol value to be equals to symbol');
    expect(await quote1UpdatePage.getPriceInput()).to.eq('5', 'Expected price value to be equals to 5');
    expect(await quote1UpdatePage.getLastTradeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastTrade value to be equals to 2000-12-31'
    );

    await quote1UpdatePage.save();
    expect(await quote1UpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await quote1ComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Quote1', async () => {
    const nbButtonsBeforeDelete = await quote1ComponentsPage.countDeleteButtons();
    await quote1ComponentsPage.clickOnLastDeleteButton();

    quote1DeleteDialog = new Quote1DeleteDialog();
    expect(await quote1DeleteDialog.getDialogTitle()).to.eq('haryaliApp.sellerQuote1.delete.question');
    await quote1DeleteDialog.clickOnConfirmButton();

    expect(await quote1ComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
