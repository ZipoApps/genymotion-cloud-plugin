package com.catviewer

import org.cef.browser.{CefBrowser, CefFrame}
import org.cef.callback.{CefAuthCallback, CefCallback, CefSchemeHandlerFactory}
import org.cef.handler.{CefLoadHandler, CefRequestHandler, CefResourceHandler, CefResourceRequestHandler}
import org.cef.network.CefRequest


class CustomSchemeHandlerFactory extends CefSchemeHandlerFactory {
  override def create(
    cefBrowser: CefBrowser,
    cefFrame:   CefFrame,
    s:          String,
    cefRequest: CefRequest
  ): CefResourceHandler = {
    new CustomResourceHandler()
  }
}