import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ImagesComponentsPage, ImagesDeleteDialog, ImagesUpdatePage } from './images.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Images e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let imagesComponentsPage: ImagesComponentsPage;
  let imagesUpdatePage: ImagesUpdatePage;
  let imagesDeleteDialog: ImagesDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Images', async () => {
    await navBarPage.goToEntity('images');
    imagesComponentsPage = new ImagesComponentsPage();
    await browser.wait(ec.visibilityOf(imagesComponentsPage.title), 5000);
    expect(await imagesComponentsPage.getTitle()).to.eq('haryaliApp.sellerImages.home.title');
    await browser.wait(ec.or(ec.visibilityOf(imagesComponentsPage.entities), ec.visibilityOf(imagesComponentsPage.noResult)), 1000);
  });

  it('should load create Images page', async () => {
    await imagesComponentsPage.clickOnCreateButton();
    imagesUpdatePage = new ImagesUpdatePage();
    expect(await imagesUpdatePage.getPageTitle()).to.eq('haryaliApp.sellerImages.home.createOrEditLabel');
    await imagesUpdatePage.cancel();
  });

  it('should create and save Images', async () => {
    const nbButtonsBeforeCreate = await imagesComponentsPage.countDeleteButtons();

    await imagesComponentsPage.clickOnCreateButton();

    await promise.all([
      imagesUpdatePage.setImageInput(absolutePath),
      imagesUpdatePage.setThumbImageInput(absolutePath),
      imagesUpdatePage.setAltInput('alt'),
      imagesUpdatePage.setTitleInput('title'),
      imagesUpdatePage.productSelectLastOption(),
    ]);

    expect(await imagesUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);
    expect(await imagesUpdatePage.getThumbImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected ThumbImage value to be end with ' + fileNameToUpload
    );
    expect(await imagesUpdatePage.getAltInput()).to.eq('alt', 'Expected Alt value to be equals to alt');
    expect(await imagesUpdatePage.getTitleInput()).to.eq('title', 'Expected Title value to be equals to title');

    await imagesUpdatePage.save();
    expect(await imagesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await imagesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Images', async () => {
    const nbButtonsBeforeDelete = await imagesComponentsPage.countDeleteButtons();
    await imagesComponentsPage.clickOnLastDeleteButton();

    imagesDeleteDialog = new ImagesDeleteDialog();
    expect(await imagesDeleteDialog.getDialogTitle()).to.eq('haryaliApp.sellerImages.delete.question');
    await imagesDeleteDialog.clickOnConfirmButton();

    expect(await imagesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
