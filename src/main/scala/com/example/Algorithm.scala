package com.example

import com.example.CampaignsData.data

object Algorithm {

  def bid(request: BidRequest): Option[BidResponse] = {
    var res = data.filter(camp => filterTargetSites(request, camp))


  }

  private def filterBidRequest(request: BidRequest, campaign: Campaign): Boolean = {

  }

  private def filterTargetSites(request: BidRequest, campaign: Campaign): Boolean =
    campaign.targeting.targetedSiteIds.contains(request.site.id)

  private def filterUserCountry(request: BidRequest, campaign: Campaign): Boolean =
    request.user match {
      case None => true
      case Some(user) => user.geo match {
        case None => true
        case Some(geo) => geo.country match {
          case None => true
          case Some(country) => campaign.country.equalsIgnoreCase(country)
        }
      }
    }


  private def filterDeviceCountry(request: BidRequest, campaign: Campaign): Boolean =
    request.device match {
      case None => true
      case Some(device) => device.geo match {
        case None => true
        case Some(geo) => geo.country match {
          case None => true
          case Some(country) => campaign.country.equalsIgnoreCase(country)
        }
      }
    }

  private def filterBidFloor(request: BidRequest, campaign: Campaign): Boolean =
    request.imp match {
      case None => true
      case Some(impressions) => impressions.exists(imp => imp.bidFloor match {
        case None => true
        case Some(bid) => campaign.bid >= bid
      })
    }


  private def filterPlacementInfo(request: BidRequest, campaign: Campaign): Boolean = {
    request.imp match {
      case None => true
      case Some(impressions) => impressions.exists(imp => {
        campaign.banners.exists(banner => {
          if (imp.w.isDefined && imp.h.isDefined && banner.height == imp.h.get && banner.width == imp.w.get) {
            true
          } else if (imp.hmax.isDefined && imp.hmin.isDefined &&) {
            true
          } else {
            false
          }
        })
      })
    }
  }
}

