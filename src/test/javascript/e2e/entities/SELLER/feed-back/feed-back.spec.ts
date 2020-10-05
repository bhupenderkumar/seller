import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { FeedBackComponentsPage, FeedBackDeleteDialog, FeedBackUpdatePage } from './feed-back.page-object';

const expect = chai.expect;

describe('FeedBack e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let feedBackComponentsPage: FeedBackComponentsPage;
  let feedBackUpdatePage: FeedBackUpdatePage;
  let feedBackDeleteDialog: FeedBackDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FeedBacks', async () => {
    await navBarPage.goToEntity('feed-back');
    feedBackComponentsPage = new FeedBackComponentsPage();
    await browser.wait(ec.visibilityOf(feedBackComponentsPage.title), 5000);
    expect(await feedBackComponentsPage.getTitle()).to.eq('haryaliApp.sellerFeedBack.home.title');
    await browser.wait(ec.or(ec.visibilityOf(feedBackComponentsPage.entities), ec.visibilityOf(feedBackComponentsPage.noResult)), 1000);
  });

  it('should load create FeedBack page', async () => {
    await feedBackComponentsPage.clickOnCreateButton();
    feedBackUpdatePage = new FeedBackUpdatePage();
    expect(await feedBackUpdatePage.getPageTitle()).to.eq('haryaliApp.sellerFeedBack.home.createOrEditLabel');
    await feedBackUpdatePage.cancel();
  });

  it('should create and save FeedBacks', async () => {
    const nbButtonsBeforeCreate = await feedBackComponentsPage.countDeleteButtons();

    await feedBackComponentsPage.clickOnCreateButton();

    await promise.all([
      feedBackUpdatePage.setRatingInput('5'),
      feedBackUpdatePage.setUserNameInput('userName'),
      feedBackUpdatePage.setUserCommentsInput('userComments'),
      feedBackUpdatePage.productSelectLastOption(),
    ]);

    expect(await feedBackUpdatePage.getRatingInput()).to.eq('5', 'Expected rating value to be equals to 5');
    expect(await feedBackUpdatePage.getUserNameInput()).to.eq('userName', 'Expected UserName value to be equals to userName');
    expect(await feedBackUpdatePage.getUserCommentsInput()).to.eq(
      'userComments',
      'Expected UserComments value to be equals to userComments'
    );

    await feedBackUpdatePage.save();
    expect(await feedBackUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await feedBackComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last FeedBack', async () => {
    const nbButtonsBeforeDelete = await feedBackComponentsPage.countDeleteButtons();
    await feedBackComponentsPage.clickOnLastDeleteButton();

    feedBackDeleteDialog = new FeedBackDeleteDialog();
    expect(await feedBackDeleteDialog.getDialogTitle()).to.eq('haryaliApp.sellerFeedBack.delete.question');
    await feedBackDeleteDialog.clickOnConfirmButton();

    expect(await feedBackComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
