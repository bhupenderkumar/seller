import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { TransactionComponentsPage, TransactionDeleteDialog, TransactionUpdatePage } from './transaction.page-object';

const expect = chai.expect;

describe('Transaction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let transactionComponentsPage: TransactionComponentsPage;
  let transactionUpdatePage: TransactionUpdatePage;
  let transactionDeleteDialog: TransactionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Transactions', async () => {
    await navBarPage.goToEntity('transaction');
    transactionComponentsPage = new TransactionComponentsPage();
    await browser.wait(ec.visibilityOf(transactionComponentsPage.title), 5000);
    expect(await transactionComponentsPage.getTitle()).to.eq('haryaliApp.sellerTransaction.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(transactionComponentsPage.entities), ec.visibilityOf(transactionComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Transaction page', async () => {
    await transactionComponentsPage.clickOnCreateButton();
    transactionUpdatePage = new TransactionUpdatePage();
    expect(await transactionUpdatePage.getPageTitle()).to.eq('haryaliApp.sellerTransaction.home.createOrEditLabel');
    await transactionUpdatePage.cancel();
  });

  it('should create and save Transactions', async () => {
    const nbButtonsBeforeCreate = await transactionComponentsPage.countDeleteButtons();

    await transactionComponentsPage.clickOnCreateButton();

    await promise.all([
      transactionUpdatePage.setUserNameInput('userName'),
      transactionUpdatePage.setTransactionAmountInput('5'),
      transactionUpdatePage.setTransactionDateInput('2000-12-31'),
      transactionUpdatePage.transactionMethodSelectLastOption(),
      transactionUpdatePage.setProcessedByInput('processedBy'),
      transactionUpdatePage.nurserySelectLastOption(),
    ]);

    expect(await transactionUpdatePage.getUserNameInput()).to.eq('userName', 'Expected UserName value to be equals to userName');
    expect(await transactionUpdatePage.getTransactionAmountInput()).to.eq('5', 'Expected transactionAmount value to be equals to 5');
    expect(await transactionUpdatePage.getTransactionDateInput()).to.eq(
      '2000-12-31',
      'Expected transactionDate value to be equals to 2000-12-31'
    );
    expect(await transactionUpdatePage.getProcessedByInput()).to.eq(
      'processedBy',
      'Expected ProcessedBy value to be equals to processedBy'
    );

    await transactionUpdatePage.save();
    expect(await transactionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await transactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Transaction', async () => {
    const nbButtonsBeforeDelete = await transactionComponentsPage.countDeleteButtons();
    await transactionComponentsPage.clickOnLastDeleteButton();

    transactionDeleteDialog = new TransactionDeleteDialog();
    expect(await transactionDeleteDialog.getDialogTitle()).to.eq('haryaliApp.sellerTransaction.delete.question');
    await transactionDeleteDialog.clickOnConfirmButton();

    expect(await transactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
