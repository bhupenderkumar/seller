import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { NurseryComponentsPage, NurseryDeleteDialog, NurseryUpdatePage } from './nursery.page-object';

const expect = chai.expect;

describe('Nursery e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let nurseryComponentsPage: NurseryComponentsPage;
  let nurseryUpdatePage: NurseryUpdatePage;
  let nurseryDeleteDialog: NurseryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Nurseries', async () => {
    await navBarPage.goToEntity('nursery');
    nurseryComponentsPage = new NurseryComponentsPage();
    await browser.wait(ec.visibilityOf(nurseryComponentsPage.title), 5000);
    expect(await nurseryComponentsPage.getTitle()).to.eq('haryaliApp.sellerNursery.home.title');
    await browser.wait(ec.or(ec.visibilityOf(nurseryComponentsPage.entities), ec.visibilityOf(nurseryComponentsPage.noResult)), 1000);
  });

  it('should load create Nursery page', async () => {
    await nurseryComponentsPage.clickOnCreateButton();
    nurseryUpdatePage = new NurseryUpdatePage();
    expect(await nurseryUpdatePage.getPageTitle()).to.eq('haryaliApp.sellerNursery.home.createOrEditLabel');
    await nurseryUpdatePage.cancel();
  });

  it('should create and save Nurseries', async () => {
    const nbButtonsBeforeCreate = await nurseryComponentsPage.countDeleteButtons();

    await nurseryComponentsPage.clickOnCreateButton();

    await promise.all([
      nurseryUpdatePage.setNurseryNameInput('nurseryName'),
      nurseryUpdatePage.setHouseNoInput('houseNo'),
      nurseryUpdatePage.setSalutationInput('salutation'),
      nurseryUpdatePage.setFirstNameInput('firstName'),
      nurseryUpdatePage.setLastNameInput('lastName'),
      nurseryUpdatePage.setMiddleNameInput('middleName'),
      nurseryUpdatePage.setStreetNoInput('streetNo'),
      nurseryUpdatePage.setDistrictNoInput('districtNo'),
      nurseryUpdatePage.setCityInput('city'),
      nurseryUpdatePage.setStateInput('state'),
      nurseryUpdatePage.countrySelectLastOption(),
      nurseryUpdatePage.setLatitudeInput('5'),
      nurseryUpdatePage.setLongitudeInput('5'),
      nurseryUpdatePage.setAddharNoInput('addharNo'),
      nurseryUpdatePage.setPanCardNoInput('panCardNo'),
      nurseryUpdatePage.payMentModeSelectLastOption(),
      nurseryUpdatePage.setUpiIdInput('upiId'),
      nurseryUpdatePage.payMentDurationSelectLastOption(),
      nurseryUpdatePage.setAccountNoInput('accountNo'),
      nurseryUpdatePage.setBankIFSCInput('bankIFSC'),
      nurseryUpdatePage.setBankNameInput('bankName'),
      nurseryUpdatePage.setCreatedDateInput('2000-12-31'),
      nurseryUpdatePage.setUpdatedDateInput('2000-12-31'),
      nurseryUpdatePage.setUserNameInput('userName'),
    ]);

    expect(await nurseryUpdatePage.getNurseryNameInput()).to.eq('nurseryName', 'Expected NurseryName value to be equals to nurseryName');
    expect(await nurseryUpdatePage.getHouseNoInput()).to.eq('houseNo', 'Expected HouseNo value to be equals to houseNo');
    expect(await nurseryUpdatePage.getSalutationInput()).to.eq('salutation', 'Expected Salutation value to be equals to salutation');
    expect(await nurseryUpdatePage.getFirstNameInput()).to.eq('firstName', 'Expected FirstName value to be equals to firstName');
    expect(await nurseryUpdatePage.getLastNameInput()).to.eq('lastName', 'Expected LastName value to be equals to lastName');
    expect(await nurseryUpdatePage.getMiddleNameInput()).to.eq('middleName', 'Expected MiddleName value to be equals to middleName');
    expect(await nurseryUpdatePage.getStreetNoInput()).to.eq('streetNo', 'Expected StreetNo value to be equals to streetNo');
    expect(await nurseryUpdatePage.getDistrictNoInput()).to.eq('districtNo', 'Expected DistrictNo value to be equals to districtNo');
    expect(await nurseryUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
    expect(await nurseryUpdatePage.getStateInput()).to.eq('state', 'Expected State value to be equals to state');
    expect(await nurseryUpdatePage.getLatitudeInput()).to.eq('5', 'Expected latitude value to be equals to 5');
    expect(await nurseryUpdatePage.getLongitudeInput()).to.eq('5', 'Expected longitude value to be equals to 5');
    expect(await nurseryUpdatePage.getAddharNoInput()).to.eq('addharNo', 'Expected AddharNo value to be equals to addharNo');
    expect(await nurseryUpdatePage.getPanCardNoInput()).to.eq('panCardNo', 'Expected PanCardNo value to be equals to panCardNo');
    expect(await nurseryUpdatePage.getUpiIdInput()).to.eq('upiId', 'Expected UpiId value to be equals to upiId');
    expect(await nurseryUpdatePage.getAccountNoInput()).to.eq('accountNo', 'Expected AccountNo value to be equals to accountNo');
    expect(await nurseryUpdatePage.getBankIFSCInput()).to.eq('bankIFSC', 'Expected BankIFSC value to be equals to bankIFSC');
    expect(await nurseryUpdatePage.getBankNameInput()).to.eq('bankName', 'Expected BankName value to be equals to bankName');
    expect(await nurseryUpdatePage.getCreatedDateInput()).to.eq('2000-12-31', 'Expected createdDate value to be equals to 2000-12-31');
    expect(await nurseryUpdatePage.getUpdatedDateInput()).to.eq('2000-12-31', 'Expected updatedDate value to be equals to 2000-12-31');
    expect(await nurseryUpdatePage.getUserNameInput()).to.eq('userName', 'Expected UserName value to be equals to userName');

    await nurseryUpdatePage.save();
    expect(await nurseryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await nurseryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Nursery', async () => {
    const nbButtonsBeforeDelete = await nurseryComponentsPage.countDeleteButtons();
    await nurseryComponentsPage.clickOnLastDeleteButton();

    nurseryDeleteDialog = new NurseryDeleteDialog();
    expect(await nurseryDeleteDialog.getDialogTitle()).to.eq('haryaliApp.sellerNursery.delete.question');
    await nurseryDeleteDialog.clickOnConfirmButton();

    expect(await nurseryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
