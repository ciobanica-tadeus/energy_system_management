import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnsubComponent } from './unsub.component';

describe('UnsubComponent', () => {
  let component: UnsubComponent;
  let fixture: ComponentFixture<UnsubComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UnsubComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(UnsubComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
