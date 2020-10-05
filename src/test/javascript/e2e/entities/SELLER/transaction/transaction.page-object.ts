import { element, by, ElementFinder } from 'protractor';

export class TransactionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-transaction div table .btn-danger'));
  title = element.all(by.css('jhi-transaction div h2#page-heading span')).first();
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

export class TransactionUpdatePage {
  pageTitle = element(by.id('jhi-transaction-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  userNameInput = element(by.id('field_userName'));
  transactionAmountInput = element(by.id('field_transactionAmount'));
  transactionDateInput = element(by.id('field_transactionDate'));
  transactionMethodSelect = element(by.id('field_transactionMethod'));
  processedByInput = element(by.id('field_processedBy'));

  nurserySelect = element(by.id('field_nursery'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUserNameInput(userName: string): Promise<void> {
    await this.userNameInput.sendKeys(userName);
  }

  async getUserNameInput(): Promise<string> {
    return await this.userNameInput.getAttribute('value');
  }

  async setTransactionAmountInput(transactionAmount: string): Promise<void> {
    await this.transactionAmountInput.sendKeys(transactionAmount);
  }

  async getTransactionAmountInput(): Promise<string> {
    return await this.transactionAmountInput.getAttribute('value');
  }

  async setTransactionDateInput(transactionDate: string): Promise<void> {
    await this.transactionDateInput.sendKeys(transactionDate);
  }

  async getTransactionDateInput(): Promise<string> {
    return await this.transactionDateInput.getAttribute('value');
  }

  async setTransactionMethodSelect(transactionMethod: string): Promise<void> {
    await this.transactionMethodSelect.sendKeys(transactionMethod);
  }

  async getTransactionMethodSelect(): Promise<string> {
    return await this.transactionMethodSelect.element(by.css('option:checked')).getText();
  }

  async transactionMethodSelectLastOption(): Promise<void> {
    await this.transactionMethodSelect.all(by.tagName('option')).last().click();
  }

  async setProcessedByInput(processedBy: string): Promise<void> {
    await this.processedByInput.sendKeys(processedBy);
  }

  async getProcessedByInput(): Promise<string> {
    return await this.processedByInput.getAttribute('value');
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

export class TransactionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-transaction-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-transaction'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
