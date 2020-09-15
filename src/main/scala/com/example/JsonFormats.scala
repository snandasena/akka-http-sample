package com.example


import spray.json.DefaultJsonProtocol

object JsonFormats {
  // import the default encoders for primitive types (Int, String, Lists etc)

  import DefaultJsonProtocol._

  // Bid request dependencies marshelling
  implicit val bidImpressionJsonFormat = jsonFormat9(Impression)
  implicit val bidSiteJsonFormat = jsonFormat2(Site)
  implicit val bidGeoJsonFormat = jsonFormat1(Geo)
  implicit val bidUserJsonFormat = jsonFormat2(User)
  implicit val bidDeviceJsonFormat = jsonFormat2(Device)

  implicit val bidRequestJsonFormat = jsonFormat5(BidRequest)

  // Bid response dependencies marshelling

  implicit val bidBannerJsonFormat = jsonFormat4(Banner)
  implicit val bidResponseJsonFormat = jsonFormat5(BidResponse)

}


