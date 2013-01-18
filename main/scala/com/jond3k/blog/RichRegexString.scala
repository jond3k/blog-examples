package com.jond3k.blog

import util.matching.Regex

/**
 * 
 */
class RichRegexString(str: String) {
  def ri: Regex = ("(?i)%s" format str).r
  def rbi: Regex = ("(?i)\\b%s\\b" format str).r
  def rb: Regex = ("\\b%s\\b" format str).r
}
