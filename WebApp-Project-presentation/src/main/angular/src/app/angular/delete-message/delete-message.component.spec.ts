import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteMessageComponent } from './delete-message.component';

describe('DeleteMessageComponent', () => {
  let component: DeleteMessageComponent;
  let fixture: ComponentFixture<DeleteMessageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteMessageComponent]
    });
    fixture = TestBed.createComponent(DeleteMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
