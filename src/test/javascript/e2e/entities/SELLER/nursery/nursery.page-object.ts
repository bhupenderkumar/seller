import { element, by, ElementFinder } from 'protractor';

export class NurseryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-nursery div table .btn-danger'));
  title = element.all(by.css('jhi-nursery div h2#page-heading span')).first();
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

export class NurseryUpdatePage {
  pageTitle = element(by.id('jhi-nursery-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nurseryNameInput = element(by.id('field_nurseryName'));
  houseNoInput = element(by.id('field_houseNo'));
  salutationInput = element(by.id('field_salutation'));
  firstNameInput = element(by.id('field_firstName'));
  lastNameInput = element(by.id('field_lastName'));
  middleNameInput = element(by.id('field_middleName'));
  streetNoInput = element(by.id('field_streetNo'));
  districtNoInput = element(by.id('field_districtNo'));
  cityInput = element(by.id('field_city'));
  stateInput = element(by.id('field_state'));
  countrySelect = element(by.id('field_country'));
  latitudeInput = element(by.id('field_latitude'));
  longitudeInput = element(by.id('field_longitude'));
  addharNoInput = element(by.id('field_addharNo'));
  panCardNoInput = element(by.id('field_panCardNo'));
  payMentModeSelect = element(by.id('field_payMentMode'));
  upiIdInput = element(by.id('field_upiId'));
  payMentDurationSelect = element(by.id('field_payMentDuration'));
  accountNoInput = element(by.id('field_accountNo'));
  bankIFSCInput = element(by.id('field_bankIFSC'));
  bankNameInput = element(by.id('field_bankName'));
  createdDateInput = element(by.id('field_createdDate'));
  updatedDateInput = element(by.id('field_updatedDate'));
  userNameInput = element(by.id('field_userName'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNurseryNameInput(nurseryName: string): Promise<void> {
    await this.nurseryNameInput.sendKeys(nurseryName);
  }

  async getNurseryNameInput(): Promise<string> {
    return await this.nurseryNameInput.getAttribute('value');
  }

  async setHouseNoInput(houseNo: string): Promise<void> {
    await this.houseNoInput.sendKeys(houseNo);
  }

  async getHouseNoInput(): Promise<string> {
    return await this.houseNoInput.getAttribute('value');
  }

  async setSalutationInput(salutation: string): Promise<void> {
    await this.salutationInput.sendKeys(salutation);
  }

  async getSalutationInput(): Promise<string> {
    return await this.salutationInput.getAttribute('value');
  }

  async setFirstNameInput(firstName: string): Promise<void> {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput(): Promise<string> {
    return await this.firstNameInput.getAttribute('value');
  }

  async setLastNameInput(lastName: string): Promise<void> {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput(): Promise<string> {
    return await this.lastNameInput.getAttribute('value');
  }

  async setMiddleNameInput(middleName: string): Promise<void> {
    await this.middleNameInput.sendKeys(middleName);
  }

  async getMiddleNameInput(): Promise<string> {
    return await this.middleNameInput.getAttribute('value');
  }

  async setStreetNoInput(streetNo: string): Promise<void> {
    await this.streetNoInput.sendKeys(streetNo);
  }

  async getStreetNoInput(): Promise<string> {
    return await this.streetNoInput.getAttribute('value');
  }

  async setDistrictNoInput(districtNo: string): Promise<void> {
    await this.districtNoInput.sendKeys(districtNo);
  }

  async getDistrictNoInput(): Promise<string> {
    return await this.districtNoInput.getAttribute('value');
  }

  async setCityInput(city: string): Promise<void> {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput(): Promise<string> {
    return await this.cityInput.getAttribute('value');
  }

  async setStateInput(state: string): Promise<void> {
    await this.stateInput.sendKeys(state);
  }

  async getStateInput(): Promise<string> {
    return await this.stateInput.getAttribute('value');
  }

  async setCountrySelect(country: string): Promise<void> {
    await this.countrySelect.sendKeys(country);
  }

  async getCountrySelect(): Promise<string> {
    return await this.countrySelect.element(by.css('option:checked')).getText();
  }

  async countrySelectLastOption(): Promise<void> {
    await this.countrySelect.all(by.tagName('option')).last().click();
  }

  async setLatitudeInput(latitude: string): Promise<void> {
    await this.latitudeInput.sendKeys(latitude);
  }

  async getLatitudeInput(): Promise<string> {
    return await this.latitudeInput.getAttribute('value');
  }

  async setLongitudeInput(longitude: string): Promise<void> {
    await this.longitudeInput.sendKeys(longitude);
  }

  async getLongitudeInput(): Promise<string> {
    return await this.longitudeInput.getAttribute('value');
  }

  async setAddharNoInput(addharNo: string): Promise<void> {
    await this.addharNoInput.sendKeys(addharNo);
  }

  async getAddharNoInput(): Promise<string> {
    return await this.addharNoInput.getAttribute('value');
  }

  async setPanCardNoInput(panCardNo: string): Promise<void> {
    await this.panCardNoInput.sendKeys(panCardNo);
  }

  async getPanCardNoInput(): Promise<string> {
    return await this.panCardNoInput.getAttribute('value');
  }

  async setPayMentModeSelect(payMentMode: string): Promise<void> {
    await this.payMentModeSelect.sendKeys(payMentMode);
  }

  async getPayMentModeSelect(): Promise<string> {
    return await this.payMentModeSelect.element(by.css('option:checked')).getText();
  }

  async payMentModeSelectLastOption(): Promise<void> {
    await this.payMentModeSelect.all(by.tagName('option')).last().click();
  }

  async setUpiIdInput(upiId: string): Promise<void> {
    await this.upiIdInput.sendKeys(upiId);
  }

  async getUpiIdInput(): Promise<string> {
    return await this.upiIdInput.getAttribute('value');
  }

  async setPayMentDurationSelect(payMentDuration: string): Promise<void> {
    await this.payMentDurationSelect.sendKeys(payMentDuration);
  }

  async getPayMentDurationSelect(): Promise<string> {
    return await this.payMentDurationSelect.element(by.css('option:checked')).getText();
  }

  async payMentDurationSelectLastOption(): Promise<void> {
    await this.payMentDurationSelect.all(by.tagName('option')).last().click();
  }

  async setAccountNoInput(accountNo: string): Promise<void> {
    await this.accountNoInput.sendKeys(accountNo);
  }

  async getAccountNoInput(): Promise<string> {
    return await this.accountNoInput.getAttribute('value');
  }

  async setBankIFSCInput(bankIFSC: string): Promise<void> {
    await this.bankIFSCInput.sendKeys(bankIFSC);
  }

  async getBankIFSCInput(): Promise<string> {
    return await this.bankIFSCInput.getAttribute('value');
  }

  async setBankNameInput(bankName: string): Promise<void> {
    await this.bankNameInput.sendKeys(bankName);
  }

  async getBankNameInput(): Promise<string> {
    return await this.bankNameInput.getAttribute('value');
  }

  async setCreatedDateInput(createdDate: string): Promise<void> {
    await this.createdDateInput.sendKeys(createdDate);
  }

  async getCreatedDateInput(): Promise<string> {
    return await this.createdDateInput.getAttribute('value');
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

export class NurseryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-nursery-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-nursery'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
