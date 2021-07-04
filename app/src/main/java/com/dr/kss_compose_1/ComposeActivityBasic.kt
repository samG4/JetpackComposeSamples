package com.dr.kss_compose_1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.dr.kss_compose_1.ui.theme.KSS_Compose_1Theme

class ComposeActivityBasic : ComponentActivity() {
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KSS_Compose_1Theme(darkTheme = false) {
                window.statusBarColor = Color.White.toArgb()
                window.statusBarColor = Color.White.toArgb()
                Surface(color = MaterialTheme.colors.background) {
                    MainView()
                }
            }
        }
    }


}

data class Test(
    val isVisible: Boolean = true,
    val name: String = ""
)

val resoureList = listOf(R.drawable.guitar_light, R.drawable.guitar_orange, R.drawable.guitar_dark)

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainView() {
    val selectedColor = rememberSaveable { mutableStateOf(0) }

    fun changeColor(value: Int) {
        selectedColor.value = value
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                //This is how we create references to any view in jetpack compose to use the constraints
                val (image, dimension1, dimension2, price, colorRow, bottomCard) = createRefs()

                //usage of constraint chaining
                createVerticalChain(dimension1, dimension2, chainStyle = ChainStyle.Packed(0.4f))

                Column(
                    modifier = Modifier.constrainAs(image) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                ) {
                    when (selectedColor.value) {
                        0 -> {
                            AnimatedVisibility(visible = selectedColor.value == 0) {
                                Guitar(id = resoureList[0])
                            }
                        }
                        1 -> {
                            AnimatedVisibility(visible = selectedColor.value == 1) {
                                Guitar(id = resoureList[1])
                            }
                        }
                        2 -> {
                            AnimatedVisibility(visible = selectedColor.value == 2) {
                                Guitar(id = resoureList[2])
                            }
                        }
                    }
                }
                DimensionText(
                    modifier = Modifier
                        .padding(end = 50.dp)
                        .constrainAs(dimension1) {
                            centerHorizontallyTo(parent)
                            top.linkTo(parent.top)
                        },
                    dimenProp = "Length",
                    dimenValue = "630mm"
                )

                DimensionText(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .constrainAs(dimension2) {
                            top.linkTo(dimension1.bottom)
                            start.linkTo(dimension1.start)
                            bottom.linkTo(image.bottom)
                        },
                    dimenProp = "Width",
                    dimenValue = "43 mm"
                )

                Text("Rs 22,000", modifier = Modifier.constrainAs(price) {
                    start.linkTo(parent.start, margin = 20.dp)
                    bottom.linkTo(colorRow.top, margin = 20.dp)

                }, fontWeight = FontWeight.Black, fontSize = 24.sp, letterSpacing = 2.sp)

                Row(modifier = Modifier.constrainAs(colorRow) {
                    start.linkTo(price.start)
                    bottom.linkTo(image.bottom, 0.dp)
                }) {
                    ColorVariant(
                        R.drawable.guitar_light_color,
                        desc = "light color"
                    ) { changeColor(0) }

                    ColorVariant(
                        R.drawable.guitar_orange_color,
                        desc = "oragne color"
                    ) { changeColor(1) }

                    ColorVariant(
                        R.drawable.guitar_dark_color,
                        desc = "dark color"
                    ) { changeColor(2) }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(
                            top = 28.dp,
                        )
                        .background(
                            color = Color(if (selectedColor.value == 0) 0xFFE7B99A else if (selectedColor.value == 1) 0xFFD45200 else 0xFF221716).copy(
                                alpha = 0.75F
                            ),
                            shape = RoundedCornerShape(
                                topStart = 60.dp,
                                bottomStart = 60.dp
                            )
                        )
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF9F9F9F).copy(alpha = 0.70F),
                                    Color(
                                        0xFFFFFFFF
                                    ).copy(alpha = 0.50F),
                                    Color(
                                        0xFFFFFFFF
                                    ).copy(alpha = 1.0F),
                                ),
                            ),
                            shape = RoundedCornerShape(
                                topStart = 60.dp,
                                bottomStart = 60.dp
                            )
                        )
                        .padding(
                            bottom = 28.dp
                        )
                        .align(Alignment.BottomCenter)
                ) {
                    Column() {
                        Text(
                            "Fender acoustic guitar",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 30.dp, vertical = 16.dp),
                            textDecoration = TextDecoration.Underline
                        )
                        BulletText("Thin Line Sleek Body, Glossy finish, Number of frets - 21, 630mm Acoustic Guitar with Dual Action Truss Rod which gives you the liberty to adjust the action of your neck in either direction if warped.")
                        BulletText("Included components: Acoustic Guitar with strap, Bag, Strings and 2 Picks")
                        BulletText("Linden binding and full wood construction with geared tuning, wood frame and steel strings")
                    }

                }

                FloatingActionButton(
                    onClick = { }, modifier = Modifier
                        .padding(end = 16.dp)
                        .size(56.dp)
                        .align(Alignment.TopEnd), backgroundColor = Color.Black
                ) {
                    Icon(
                        imageVector = Icons.Filled.Work,
                        contentDescription = "buy",
                        modifier = Modifier.background(color = Color.Transparent),
                        tint = Color.White
                    )
                }
            }
        }
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = "back",
            modifier = Modifier
                .padding(top = 20.dp, start = 16.dp)
                .clickable {
                    Log.d("Icon Tapped", "onBackClicked")
                }
                .padding(8.dp)
        )
    }
}

@Composable
fun ColorVariant(id: Int, desc: String, callback: () -> Unit) {
    Image(
        painter = painterResource(id = id),
        contentDescription = desc,
        modifier = Modifier
            .size(58.dp)
            .clickable { callback() }
    )
}

@Composable
fun BulletText(textDetails: String) {
    Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)) {
        Text(
            "●",
            fontSize = 8.sp,
            modifier = Modifier.padding(end = 8.dp, top = 6.dp)
        )
        Text(
            textDetails,
            fontSize = 16.sp
        )
    }
}

@Composable
fun DimensionText(dimenProp: String, dimenValue: String, modifier: Modifier) {
    Column(modifier = Modifier.then(modifier)) {
        Text(
            dimenProp,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            dimenValue,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun Guitar(id: Int) {
    Image(
        painter = painterResource(id = id),
        contentDescription = "Light Guitar",
        modifier = Modifier
            .height(512.dp)
            .width(220.dp)
            .animateContentSize(),
        contentScale = ContentScale.Fit
    )
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainView()
}