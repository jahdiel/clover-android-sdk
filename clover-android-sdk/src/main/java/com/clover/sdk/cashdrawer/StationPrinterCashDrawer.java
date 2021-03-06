package com.clover.sdk.cashdrawer;

import android.accounts.Account;
import android.content.Context;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.util.Platform;
import com.clover.sdk.v1.printer.Category;
import com.clover.sdk.v1.printer.Printer;
import com.clover.sdk.v1.printer.Type;

import java.util.Collections;
import java.util.Set;

class StationPrinterCashDrawer extends CashDrawer {
  static class Discovery extends CashDrawer.Discovery<StationPrinterCashDrawer> {

    protected Discovery(Context context) {
      super(context);
    }

    @Override
    public Set<StationPrinterCashDrawer> list() {
      if (!Platform.isCloverStation()) {
        return Collections.emptySet();
      }
      return Collections.singleton(new StationPrinterCashDrawer(context));
    }
  }

  private final Account cloverAccount;
  private final Printer stationPrinter;

  protected StationPrinterCashDrawer(Context context) {
    super(context, 1);
    this.cloverAccount = CloverAccount.getAccount(context);
    this.stationPrinter = new Printer.Builder().type(Type.SEIKO_USB).category(Category.RECEIPT).build();
  }

  @Override
  public boolean pop() {
    com.clover.sdk.v1.printer.CashDrawer.open(context, cloverAccount, stationPrinter);
    return true;
  }

  @Override
  public String toString() {
    return "StationPrinterCashDrawer{" +
        "cloverAccount=" + cloverAccount +
        ", stationPrinter=" + stationPrinter +
        '}';
  }
}
