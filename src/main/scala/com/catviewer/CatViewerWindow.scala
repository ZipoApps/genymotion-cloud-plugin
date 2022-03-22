package com.catviewer

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.ui.jcef.JBCefBrowser
import sys.process._

import javax.swing.{BoxLayout, JButton, JComponent, JFrame, JPanel, JTextField, JTextPane}
import org.cef.{CefApp, CefSettings}

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Dimension, FlowLayout}

case class CatViewerWindow(project: Project) {
  private lazy val webView: JBCefBrowser = {
    val browser = new JBCefBrowser()
    registerAppSchemeHandler()
    browser.loadURL("https://cloud.geny.io/launchpad")
    Disposer.register(project, browser)
    browser
  }

//  def content: JComponent = webView.getComponent
  def content: JComponent = {
    val completePanel = new JPanel()
    val topPanel = new JPanel()

    val topLayoutLayout = new FlowLayout()
    val completePanelLayout = new BoxLayout(completePanel, BoxLayout.Y_AXIS)

    topPanel.setLayout(topLayoutLayout)
    completePanel.setLayout(completePanelLayout)

    val textField = new HintJTextField("Selected device UUID")
    val btnShow = new JButton("Show")
    btnShow.addActionListener((e: ActionEvent) => {
      webView.loadURL("https://cloud.geny.io/instance/" + textField.getText)
    })

    val btnAdb = new JButton("ADB")
    btnAdb.addActionListener((e: ActionEvent) => {
      val cmd = "gmsaas instances adbconnect " + textField.getText
      val output = cmd.!!
    })

    topPanel.add(textField)
    topPanel.add(btnShow)
    topPanel.add(btnAdb)

    completePanel.add(topPanel)
    completePanel.add(webView.getComponent)

    completePanel
  }


  private def registerAppSchemeHandler(): Unit = {
    CefApp
      .getInstance()
      .registerSchemeHandlerFactory(
        "https",
        "cloud.geny",
        new CustomSchemeHandlerFactory
      )
  }
}
