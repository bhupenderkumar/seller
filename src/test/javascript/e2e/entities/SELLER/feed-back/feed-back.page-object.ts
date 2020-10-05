import { element, by, ElementFinder } from 'protractor';

export class FeedBackComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-feed-back div table .btn-danger'));
  title = element.all(by.css('jhi-feed-back div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class FeedBackUpdatePage {
  pageTitle = element(by.id('jhi-feed-back-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  ratingInput = element(by.id('field_rating'));
  userNameInput = element(by.id('field_userName'));
  userCommentsInput = element(by.id('field_userComments'));

  productSelect = element(by.id('field_product'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setRatingInput(rating: string): Promise<void> {
    await this.ratingInput.sendKeys(rating);
  }

  async getRatingInput(): Promise<string> {
    return await this.ratingInput.getAttribute('value');
  }

  async setUserNameInput(userName: string): Promise<void> {
    await this.userNameInput.sendKeys(userName);
  }

  async getUserNameInput(): Promise<string> {
    return await this.userNameInput.getAttribute('value');
  }

  async setUserCommentsInput(userComments: string): Promise<void> {
    await this.userCommentsInput.sendKeys(userComments);
  }

  async getUserCommentsInput(): Promise<string> {
    return await this.userCommentsInput.getAttribute('value');
  }

  async productSelectLastOption(): Promise<void> {
    await this.productSelect.all(by.tagName('option')).last().click();
  }

  async productSelectOption(option: string): Promise<void> {
    await this.productSelect.sendKeys(option);
  }

  getProductSelect(): ElementFinder {
    return this.productSelect;
  }

  async getProductSelectedOption(): Promise<string> {
    return await this.productSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class FeedBackDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-feedBack-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-feedBack'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
