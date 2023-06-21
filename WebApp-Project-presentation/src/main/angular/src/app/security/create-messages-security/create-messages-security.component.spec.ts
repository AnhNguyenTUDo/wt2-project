import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMessagesSecurityComponent } from './create-messages-security.component';

describe('CreateMessagesSecurityComponent', () => {
  let component: CreateMessagesSecurityComponent;
  let fixture: ComponentFixture<CreateMessagesSecurityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateMessagesSecurityComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateMessagesSecurityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
