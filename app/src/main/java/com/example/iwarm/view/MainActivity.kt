package com.example.iwarm.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iwarm.adapter.TabRecyclerAdapter
import com.example.iwarm.data.FinishFragment
import com.example.iwarm.data.response.WeatherListResponse
import com.example.iwarm.databinding.ActivityMainBinding
import com.example.iwarm.viewmodel.WeatherViewModel
import com.google.android.gms.location.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy {
        ViewModelProvider(this, WeatherViewModel.Factory(
            application, lat.toDouble(), lot.toDouble(), "95dabcdab4c88faa10d407fc8d42cced"))[WeatherViewModel::class.java]
    }

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null   // 현재 위치 가져오기 변수
    private val REQUEST_PERMISSION_LOCATION = 10
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    private lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private lateinit var lat: String
    private lateinit var lot: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mLocationRequest =  LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        // 현재 위치 가져오기
        if (checkPermissionForLocation(this)) {
            startLocationUpdates()
        }

        getCurrentTime()
        setBottomSheetBehavior()
    }

    // 현재 시간을 가져옵니다.
    private fun getCurrentTime() {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val format = SimpleDateFormat("HH:mm a", Locale.KOREA)

        binding.currentTime.text = format.format(date)
    }

    // bottom sheet 를 설정해준다.
    private fun setBottomSheetBehavior() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        val radius = binding.bottomSheet.radius

        bottomSheetBehavior.addBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    supportFragmentManager.beginTransaction()
                        .add(com.example.iwarm.R.id.bottom_sheet, SuggestClothesFragment())
                        .commit()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset >= 0) {
                    // radius 를 줄임
                    binding.bottomSheet.radius = radius - (radius * slideOffset)
                    // 화살표를 펼치면서 돌아가게
                    binding.arrowImage.rotation = (1-slideOffset) * 180F
                    // 글자가 점점 사라지게
                    binding.recommendClothes.alpha = 1 - slideOffset * 2.3F
                    // 내용의 투명도
                    binding.fragmentFrame.alpha = Math.min(slideOffset * 2F, 1F)
                }
            }
        })
    }

    private fun getWeather() {
        viewModel.getWeatherLiveData.observe(this) { response ->
            if (response != null) {
                val list = mutableListOf<WeatherListResponse>()
                list.add(response.list!![0])
                list.add(response.list[7])
                val adapter = TabRecyclerAdapter(list, this)
                binding.tabRecyclerView.adapter = adapter
                binding.tabRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            } else {
                Log.d("TAG", "getWeather: fail")
            }
        }
    }

    private fun startLocationUpdates() {
        //FusedLocationProviderClient의 인스턴스를 생성
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청
        mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }

    // 시스템으로 부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location?) {
        mLastLocation = location!!
        lat =  mLastLocation.latitude.toString() // 갱신 된 위도
        lot =  mLastLocation.longitude.toString() // 갱신 된 경도

        getWeather()

        // 현재 지역을 가져옵니다
        val gcd = Geocoder(baseContext, Locale.getDefault())
        val address: List<Address> = gcd.getFromLocation(mLastLocation.latitude, mLastLocation.longitude, 1) as List<Address>
        if (address.isNotEmpty())   // address 가 비어있지 않다면
            binding.areaName.text = address[0].adminArea    // 거주 area 를 가져온다.
    }

    // 위치 권한이 있는지 확인하는 메서드
    private fun checkPermissionForLocation(context: Context): Boolean {
        // Android 6.0 Marshmallow 이상에서는 위치 권한에 추가 런타임 권한이 필요
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                // 권한이 없으므로 권한 요청 알림 보내기
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
    }

    // 사용자에게 권한 요청 후 결과에 대한 처리 로직
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            } else {
                Log.d("ttt", "onRequestPermissionsResult() _ 권한 허용 거부")
                Toast.makeText(this, "권한이 없어 해당 기능을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}