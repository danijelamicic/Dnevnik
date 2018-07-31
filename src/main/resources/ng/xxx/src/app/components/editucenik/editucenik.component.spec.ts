import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditucenikComponent } from './editucenik.component';

describe('EditucenikComponent', () => {
  let component: EditucenikComponent;
  let fixture: ComponentFixture<EditucenikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditucenikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditucenikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
