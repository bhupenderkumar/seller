import { element, by, ElementFinder } from 'protractor';

export class ProductComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product div table .btn-danger'));
  title = element.all(by.css('jhi-product div h2#page-heading span')).first();
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

export class ProductUpdatePage {
  pageTitle = element(by.id('jhi-product-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  productNameInput = element(by.id('field_productName'));
  productDescriptionInput = element(by.id('field_productDescription'));
  priceInput = element(by.id('field_price'));
  approvalSelect = element(by.id('field_approval'));
  showStatusInput = element(by.id('field_showStatus'));
  productTypeSelect = element(by.id('field_productType'));
  videoInput = element(by.id('file_video'));
  productDateInput = element(by.id('field_productDate'));
  updatedDateInput = element(by.id('field_updatedDate'));
  userNameInput = element(by.id('field_userName'));

  nurserySelect = element(by.id('field_nursery'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setProductNameInput(productName: string): Promise<void> {
    await this.productNameInput.sendKeys(productName);
  }

  async getProductNameInput(): Promise<string> {
    return await this.productNameInput.getAttribute('value');
  }

  async setProductDescriptionInput(productDescription: string): Promise<void> {
    await this.productDescriptionInput.sendKeys(productDescription);
  }

  async getProductDescriptionInput(): Promise<string> {
    return await this.productDescriptionInput.getAttribute('value');
  }

  async setPriceInput(price: string): Promise<void> {
    await this.priceInput.sendKeys(price);
  }

  async getPriceInput(): Promise<string> {
    return await this.priceInput.getAttribute('value');
  }

  async setApprovalSelect(approval: string): Promise<void> {
    await this.approvalSelect.sendKeys(approval);
  }

  async getApprovalSelect(): Promise<string> {
    return await this.approvalSelect.element(by.css('option:checked')).getText();
  }

  async approvalSelectLastOption(): Promise<void> {
    await this.approvalSelect.all(by.tagName('option')).last().click();
  }

  getShowStatusInput(): ElementFinder {
    return this.showStatusInput;
  }

  async setProductTypeSelect(productType: string): Promise<void> {
    await this.productTypeSelect.sendKeys(productType);
  }

  async getProductTypeSelect(): Promise<string> {
    return await this.productTypeSelect.element(by.css('option:checked')).getText();
  }

  async productTypeSelectLastOption(): Promise<void> {
    await this.productTypeSelect.all(by.tagName('option')).last().click();
  }

  async setVideoInput(video: string): Promise<void> {
    await this.videoInput.sendKeys(video);
  }

  async getVideoInput(): Promise<string> {
    return await this.videoInput.getAttribute('value');
  }

  async setProductDateInput(productDate: string): Promise<void> {
    await this.productDateInput.sendKeys(productDate);
  }

  async getProductDateInput(): Promise<string> {
    return await this.productDateInput.getAttribute('value');
  }

  async setUpdatedDateInput(updatedDate: string): Promise<void> {
    await this.updatedDateInput.sendKeys(updatedDate);
  }

  async getUpdatedDateInput(): Promise<string> {
    return await this.updatedDateInput.getAttribute('value');
  }

  async setUserNameInput(userName: string): Promise<void> {
    await this.userNameInput.sendKeys(userName);
  }

  async getUserNameInput(): Promise<string> {
    return await this.userNameInput.getAttribute('value');
  }

  async nurserySelectLastOption(): Promise<void> {
    await this.nurserySelect.all(by.tagName('option')).last().click();
  }

  async nurserySelectOption(option: string): Promise<void> {
    await this.nurserySelect.sendKeys(option);
  }

  getNurserySelect(): ElementFinder {
    return this.nurserySelect;
  }

  async getNurserySelectedOption(): Promise<string> {
    return await this.nurserySelect.element(by.css('option:checked')).getText();
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

export class ProductDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-product-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-product'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
