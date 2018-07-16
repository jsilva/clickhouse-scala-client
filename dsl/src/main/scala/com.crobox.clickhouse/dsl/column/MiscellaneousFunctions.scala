package com.crobox.clickhouse.dsl.column

import com.crobox.clickhouse.dsl.{EmptyColumn, ExpressionColumn}

trait MiscellaneousFunctions { self: Magnets =>
  sealed trait MiscellaneousFunction

  abstract class MiscellaneousOp[V](col: ConstOrColMagnet)
      extends ExpressionColumn[V](col.column)
      with MiscellaneousFunction
  abstract class MiscellaneousConst[V]() extends ExpressionColumn[V](EmptyColumn()) with MiscellaneousFunction

  case class HostName()                          extends MiscellaneousConst[Long]()
  case class VisibleWidth(col: ConstOrColMagnet) extends MiscellaneousOp[Long](col)
  case class ToTypeName(col: ConstOrColMagnet)   extends MiscellaneousOp[String](col)
  case class BlockSize()                         extends MiscellaneousConst[Long]()
  case class Materialize(col: ConstOrColMagnet)  extends MiscellaneousOp[Long](col)
  case class Ignore(coln: ConstOrColMagnet*)     extends MiscellaneousConst[Long]()
  case class Sleep(col: NumericCol)              extends MiscellaneousOp[Long](col.column) //is this an operator?
  case class CurrentDatabase()                   extends MiscellaneousConst[String]()
  case class IsFinite(col: NumericCol)           extends MiscellaneousOp[Boolean](col.column)
  case class IsInfinite(col: NumericCol)         extends MiscellaneousOp[Boolean](col.column)
  case class IsNaN(col: NumericCol)              extends MiscellaneousOp[Long](col.column)
  case class HasColumnInTable(database: StringColMagnet,
                              table: StringColMagnet,
                              column: StringColMagnet,
                              hostName: Option[StringColMagnet] = None,
                              userName: Option[StringColMagnet] = None,
                              passWord: Option[StringColMagnet] = None)
      extends MiscellaneousConst[Boolean]()
  case class Bar(col: ConstOrColMagnet) extends MiscellaneousOp[String](col)
  case class Transform(col: ConstOrColMagnet,
                       arrayFrom: ArrayColMagnet,
                       arrayTo: ArrayColMagnet,
                       default: ConstOrColMagnet)
      extends MiscellaneousOp[Long](col) //TODO: Could we match the col with arrayFrom element type? Could we also do the same for arrayTo and default?
  case class FormatReadableSize(col: NumericCol)                extends MiscellaneousOp[String](col.column)
  case class Least(a: ConstOrColMagnet, b: ConstOrColMagnet)    extends MiscellaneousOp[Long](a)
  case class Greatest(a: ConstOrColMagnet, b: ConstOrColMagnet) extends MiscellaneousOp[Long](a)
  case class Uptime()                                           extends MiscellaneousConst[Long]()
  case class Version()                                          extends MiscellaneousConst[Long]()
  case class RowNumberInAllBlocks()                             extends MiscellaneousConst[Long]()
  case class RunningDifference(col: ConstOrColMagnet)           extends MiscellaneousOp[Long](col)
  case class MACNumToString(col: NumericCol)                    extends MiscellaneousOp[String](col.column)
  case class MACStringToNum(col: StringColMagnet)               extends MiscellaneousOp[Long](col.column)
  case class MACStringToOUI(col: StringColMagnet)               extends MiscellaneousOp[Long](col.column)

  def hostName()                          = HostName()
  def visibleWidth(col: ConstOrColMagnet) = VisibleWidth(col)
  def toTypeName(col: ConstOrColMagnet)   = ToTypeName(col)
  def blockSize()                         = BlockSize()
  def materialize(col: ConstOrColMagnet)  = Materialize(col)
  def ignore(coln: ConstOrColMagnet*)     = Ignore(coln: _*)
  def sleep(col: NumericCol)              = Sleep(col: NumericCol)
  def currentDatabase()                   = CurrentDatabase()
  def isFinite(col: NumericCol)           = IsFinite(col)
  def isInfinite(col: NumericCol)         = IsInfinite(col)
  def isNaN(col: NumericCol)              = IsNaN(col: NumericCol)

  def hasColumnInTable(database: StringColMagnet,
                       table: StringColMagnet,
                       column: StringColMagnet,
                       hostName: Option[StringColMagnet] = None,
                       userName: Option[StringColMagnet] = None,
                       passWord: Option[StringColMagnet] = None) =
    HasColumnInTable(database, table, column, hostName, userName, passWord)
  def bar(col: ConstOrColMagnet) = Bar(col: ConstOrColMagnet)

  def transform(col: ConstOrColMagnet, arrayFrom: ArrayColMagnet, arrayTo: ArrayColMagnet, default: ConstOrColMagnet) =
    Transform(col: ConstOrColMagnet, arrayFrom: ArrayColMagnet, arrayTo: ArrayColMagnet, default)
  def formatReadableSize(col: NumericCol)                = FormatReadableSize(col)
  def least(a: ConstOrColMagnet, b: ConstOrColMagnet)    = Least(a: ConstOrColMagnet, b)
  def greatest(a: ConstOrColMagnet, b: ConstOrColMagnet) = Greatest(a: ConstOrColMagnet, b)
  def uptime()                                           = Uptime()
  def version()                                          = Version()
  def rowNumberInAllBlocks()                             = RowNumberInAllBlocks()
  def runningDifference(col: ConstOrColMagnet)           = RunningDifference(col)
  def mACNumToString(col: NumericCol)                    = MACNumToString(col)
  def mACStringToNum(col: StringColMagnet)               = MACStringToNum(col)
  def mACStringToOUI(col: StringColMagnet)               = MACStringToOUI(col)
  /*

  hostName()
  visibleWidth(x)
  toTypeName(x)
  blockSize()
  materialize(x)
  ignore(...)
  sleep(seconds)
  currentDatabase()
  isFinite(x)
  isInfinite(x)
  isNaN(x)
  hasColumnInTable(['hostname'[, 'username'[, 'password']],] 'database', 'table', 'column')
  bar
  transform
  formatReadableSize(x)
  least(a, b)
  greatest(a, b)
  uptime()
  version()
  rowNumberInAllBlocks()
  runningDifference(x)
  MACNumToString(num)
  MACStringToNum(s)
  MACStringToOUI(s)

  arrayJoin
  tuple
 */
}
